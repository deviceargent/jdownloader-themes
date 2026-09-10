param(
    [string]$JDownloaderDir = $null
)

$ErrorActionPreference = 'Stop'

function Resolve-JdDir {
    if ( $JDownloaderDir -and ( Test-Path -LiteralPath $JDownloaderDir ) ) {
        return $JDownloaderDir
    }
    $candidate = Join-Path $env:LOCALAPPDATA 'JDownloader 2'
    if ( Test-Path -LiteralPath $candidate ) { return $candidate }
    throw "No se encontró la instalación de JDownloader 2. Pasá -JDownloaderDir <ruta>."
}

function Find-Javac {
    $cmd = Get-Command javac -ErrorAction SilentlyContinue
    if ( $cmd ) { return $cmd.Source }
    throw 'No se encontró javac en el PATH.'
}

$jdDir    = Resolve-JdDir
$jdGuiJar = Join-Path $jdDir 'libs\JDGUI.jar'
$flatJar  = Get-ChildItem (Join-Path $jdDir 'libs\laf\flatlaf*.jar') | Select-Object -First 1

if ( -not $flatJar ) { throw 'No se encontró flatlaf*.jar en libs\laf.' }
if ( -not ( Test-Path -LiteralPath $jdGuiJar ) ) { throw "No se encontró $jdGuiJar" }

$javac     = Find-Javac
$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$source    = Join-Path $scriptDir 'ConfigSidebar$1.java'
if ( -not ( Test-Path -LiteralPath $source ) ) { throw "Falta $source junto al script." }

$stage = Join-Path $env:TEMP 'pastel98-hover-build'
if ( Test-Path -LiteralPath $stage ) { Remove-Item -LiteralPath $stage -Recurse -Force }
New-Item -ItemType Directory -Path $stage | Out-Null

Write-Host 'Compilando ConfigSidebar$1 contra JDGUI.jar + flatlaf.jar ...'
$jars = @(
    $jdGuiJar,
    $flatJar.FullName,
    (Join-Path $jdDir 'JDownloader.jar'),
    (Join-Path $jdDir 'Core.jar'),
    (Get-ChildItem (Join-Path $jdDir 'libs\*.jar') | ForEach-Object { $_.FullName })
) -join ';'

& $javac -encoding UTF-8 -cp $jars -d $stage $source
if ( $LASTEXITCODE -ne 0 ) { throw "Falló la compilación (exit $LASTEXITCODE)." }

$entryPath = 'jd/gui/swing/jdgui/views/settings/sidebar/ConfigSidebar$1.class'
$built     = Join-Path $stage ($entryPath -replace '/', '\')

if ( -not ( Test-Path -LiteralPath $built ) ) {
    throw "No se generó $built. Revisá la ruta del paquete en el fuente."
}

$stamp   = Get-Date -Format 'yyyyMMdd-HHmmss'
$backup  = "$jdGuiJar.bak-$stamp"
if ( -not ( Test-Path -LiteralPath $backup ) ) {
    Copy-Item -LiteralPath $jdGuiJar -Destination $backup
    Write-Host "Backup creado: $backup"
}

Write-Host "Inyectando $entryPath en JDGUI.jar ..."
Add-Type -AssemblyName System.IO.Compression
Add-Type -AssemblyName System.IO.Compression.FileSystem

$bytes  = [System.IO.File]::ReadAllBytes( $built )
$fs     = [System.IO.File]::Open( $jdGuiJar, [System.IO.FileMode]::Open, [System.IO.FileAccess]::ReadWrite )
$zip    = [System.IO.Compression.ZipArchive]::new( $fs, [System.IO.Compression.ZipArchiveMode]::Update )

try {
    $existing = $zip.GetEntry( $entryPath )
    if ( $existing ) { $existing.Delete() }
    $entry = $zip.CreateEntry( $entryPath, [System.IO.Compression.CompressionLevel]::Optimal )
    $s = $entry.Open()
    try { $s.Write( $bytes, 0, $bytes.Length ) } finally { $s.Dispose() }
} finally {
    $zip.Dispose()
    $fs.Dispose()
}

Remove-Item -LiteralPath $stage -Recurse -Force

Write-Host ''
Write-Host 'Listo. Hover de settings parcheado (color vía ConfigSidebar.hoverBackground).'
Write-Host 'Reiniciá o cambiá de tema en JDownloader para verlo.'