<#
.SYNOPSIS
    Instalador de temas FlatLaf para JDownloader 2.
.DESCRIPTION
    Copia el tema seleccionado, inyecta clases en JDownloader.jar,
    configura la clase LAF y opcionalmente aplica el parche de progress bars.
.PARAMETER JdPath
    Ruta a la carpeta de JDownloader 2. Si no se especifica, se detecta automáticamente.
.PARAMETER Theme
    Nombre del tema a instalar. Si no se especifica, muestra un menú interactivo.
.PARAMETER PatchProgress
    Aplica el parche de ExtProgressColumn para que las barras de progreso
    hereden el color del tema.
.EXAMPLE
    .\Install-Theme.ps1 -Theme Zune -PatchProgress
.EXAMPLE
    .\Install-Theme.ps1
#>

param(
    [string]$JdPath,
    [string]$Theme,
    [switch]$PatchProgress
)

$ErrorActionPreference = "Stop"
$ScriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path

# --- Available themes ---
$Themes = @(
    @{ Name = "Airlock";   Description = "Consola industrial de cabina espacial"; HasCustomUI = $false },
    @{ Name = "Pastel98";  Description = "Paleta pastel colorida estilo Windows 98"; HasCustomUI = $true },
    @{ Name = "SunsetTape"; Description = "Estetica retrowave synthwave"; HasCustomUI = $true },
    @{ Name = "PixelFizz"; Description = "Inspirado en pixel art"; HasCustomUI = $true },
    @{ Name = "VaporGrid"; Description = "Estetica vaporwave con grid"; HasCustomUI = $true },
    @{ Name = "Zune";      Description = "Interfaz oscura estilo dispositivo Zune"; HasCustomUI = $true }
)

# --- Detect JDownloader path ---
function Find-JDownloader {
    $candidates = @(
        "$env:LOCALAPPDATA\JDownloader 2.0",
        "$env:PROGRAMFILES\JDownloader 2.0",
        "$env:PROGRAMFILES(X86)\JDownloader 2.0",
        "C:\JDownloader 2.0",
        "C:\JD2-Lab\JDownloader"
    )
    foreach ($path in $candidates) {
        if (Test-Path "$path\JDownloader.jar") {
            return $path
        }
    }
    return $null
}

# --- Select theme interactively ---
function Select-Theme {
    Write-Host ""
    Write-Host "=== Temas disponibles ===" -ForegroundColor Cyan
    Write-Host ""
    for ($i = 0; $i -lt $Themes.Count; $i++) {
        $t = $Themes[$i]
        $ui = if ($t.HasCustomUI) { " [custom UI]" } else { "" }
        Write-Host "  $($i + 1). $($t.Name) - $($t.Description)$ui"
    }
    Write-Host ""
    do {
        $choice = Read-Host "Selecciona un tema (1-$($Themes.Count))"
        $num = [int]$choice - 1
    } while ($num -lt 0 -or $num -ge $Themes.Count)
    return $Themes[$num]
}

# --- Backup JDownloader.jar ---
function Backup-JDJar {
    param([string]$JarPath)
    $timestamp = Get-Date -Format "yyyyMMdd-HHmmss"
    $backupPath = "$JarPath.bak-theme-$timestamp"
    if (-not (Test-Path $backupPath)) {
        Copy-Item $JarPath $backupPath
        Write-Host "  Backup: $backupPath" -ForegroundColor DarkGray
    }
    return $backupPath
}

# --- Inject classes into JDownloader.jar ---
function Inject-Classes {
    param(
        [string]$JarPath,
        [string]$ThemeBinPath,
        [string]$ThemeName
    )
    $packageName = $ThemeName.ToLower()
    $sourcePath = "$ThemeBinPath\com\github\deviceargent\$packageName"
    
    if (-not (Test-Path $sourcePath)) {
        Write-Host "  Clases no encontradas en $sourcePath" -ForegroundColor Yellow
        return $false
    }
    
    $tempDir = Join-Path $env:TEMP "jd-theme-inject-$ThemeName"
    if (Test-Path $tempDir) { Remove-Item $tempDir -Recurse -Force }
    
    # Copy the package structure to temp
    Copy-Item -Path "$ThemeBinPath\com" -Destination "$tempDir\com" -Recurse
    
    # Inject into JDownloader.jar
    Push-Location $tempDir
    try {
        & jar uf $JarPath -C . "com/github/deviceargent/$packageName/"
        if ($LASTEXITCODE -ne 0) {
            Write-Host "  Error al inyectar clases" -ForegroundColor Red
            return $false
        }
    } finally {
        Pop-Location
    }
    
    Remove-Item $tempDir -Recurse -Force -ErrorAction SilentlyContinue
    Write-Host "  Clases inyectadas: com.github.deviceargent.$packageName" -ForegroundColor Green
    return $true
}

# --- Apply ExtProgressColumn patch ---
function Apply-ProgressPatch {
    param([string]$JarPath)
    
    $patchFile = "$ScriptDir\patches\ExtProgressColumn.class"
    if (-not (Test-Path $patchFile)) {
        Write-Host "  Parche no encontrado: $patchFile" -ForegroundColor Yellow
        return $false
    }
    
    $tempDir = Join-Path $env:TEMP "jd-patch-progress"
    if (Test-Path $tempDir) { Remove-Item $tempDir -Recurse -Force }
    New-Item -ItemType Directory -Path "$tempDir\org\appwork\swing\exttable\columns" -Force | Out-Null
    Copy-Item $patchFile "$tempDir\org\appwork\swing\exttable\columns\ExtProgressColumn.class"
    
    Push-Location $tempDir
    try {
        & jar uf $JarPath "org/appwork/swing/exttable/columns/ExtProgressColumn.class"
        if ($LASTEXITCODE -ne 0) {
            Write-Host "  Error al aplicar parche de progreso" -ForegroundColor Red
            return $false
        }
    } finally {
        Pop-Location
    }
    
    Remove-Item $tempDir -Recurse -Force -ErrorAction SilentlyContinue
    Write-Host "  Parche ExtProgressColumn aplicado" -ForegroundColor Green
    return $true
}

# --- Update JDownloader settings ---
function Update-Settings {
    param(
        [string]$CfgPath,
        [string]$ClassName
    )
    
    $settingsFile = "$CfgPath\org.jdownloader.settings.GraphicalUserInterfaceSettings.json"
    if (-not (Test-Path $settingsFile)) {
        Write-Host "  Settings no encontrados: $settingsFile" -ForegroundColor Yellow
        return
    }
    
    $json = Get-Content $settingsFile -Raw | ConvertFrom-Json
    $json.customlookandfeelclass = $ClassName
    # Remove lookandfeeltheme to let custom class take full control
    if ($json.PSObject.Properties['lookandfeeltheme']) {
        $json.PSObject.Properties.Remove('lookandfeeltheme')
    }
    $json | ConvertTo-Json -Depth 10 | Set-Content $settingsFile
    Write-Host "  customlookandfeelclass = $ClassName" -ForegroundColor Green
}

# --- Main ---
Write-Host ""
Write-Host "=== Instalador de Temas JDownloader ===" -ForegroundColor Cyan
Write-Host ""

# Detect JD path
if (-not $JdPath) {
    $JdPath = Find-JDownloader
    if (-not $JdPath) {
        Write-Host "JDownloader no encontrado. Especifica la ruta con -JdPath" -ForegroundColor Red
        exit 1
    }
}

if (-not (Test-Path "$JdPath\JDownloader.jar")) {
    Write-Host "JDownloader.jar no encontrado en: $JdPath" -ForegroundColor Red
    exit 1
}

Write-Host "JDownloader: $JdPath" -ForegroundColor DarkGray

# Select theme
if ($Theme) {
    $selected = $Themes | Where-Object { $_.Name -eq $Theme }
    if (-not $selected) {
        Write-Host "Tema no encontrado: $Theme" -ForegroundColor Red
        Write-Host "Temas disponibles: $($Themes.Name -join ', ')" -ForegroundColor Yellow
        exit 1
    }
} else {
    $selected = Select-Theme
}

Write-Host ""
Write-Host "Tema: $($selected.Name) - $($selected.Description)" -ForegroundColor Yellow

# Paths
$packageName = $selected.Name.ToLower()
$themeJar = "$ScriptDir\themes\$($selected.Name)\Flat$($selected.Name).jar"
$themeJson = "$ScriptDir\themes\$($selected.Name)\$($selected.Name).json"
$themeBin = "$ScriptDir\themes\$($selected.Name)\bin"

# Verify theme files exist
if (-not (Test-Path $themeJar)) {
    Write-Host "Jar no encontrado: $themeJar" -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "Instalando..." -ForegroundColor Cyan

# 1. Copy theme jar to libs/laf/
$laflare = "$JdPath\libs\laf"
Copy-Item $themeJar "$laflare\Flat$($selected.Name).jar" -Force
Write-Host "  [$($selected.Name).jar] -> $laflare\" -ForegroundColor Green

# 2. Copy JSON config to cfg/laf/
if (Test-Path $themeJson) {
    $cfgDir = "$JdPath\cfg\laf"
    if (-not (Test-Path $cfgDir)) { New-Item -ItemType Directory -Path $cfgDir -Force | Out-Null }
    Copy-Item $themeJson "$cfgDir\$($selected.Name).json" -Force
    Write-Host "  [$($selected.Name).json] -> $cfgDir\" -ForegroundColor Green
}

# 3. Backup JDownloader.jar
$jarPath = "$JdPath\JDownloader.jar"
Write-Host ""
Write-Host "Backup..." -ForegroundColor Cyan
Backup-JDJar -JarPath $jarPath

# 4. Inject classes if needed
if ($selected.HasCustomUI) {
    Write-Host ""
    Write-Host "Inyectando clases..." -ForegroundColor Cyan
    if (Test-Path $themeBin) {
        Inject-Classes -JarPath $jarPath -ThemeBinPath $themeBin -ThemeName $selected.Name
    } else {
        Write-Host "  Directorio bin no encontrado: $themeBin" -ForegroundColor Yellow
        Write-Host "  Las clases custom no se inyectaran. Efectos visuales limitados." -ForegroundColor Yellow
    }
}

# 5. Apply progress patch
if ($PatchProgress) {
    Write-Host ""
    Write-Host "Parche de progreso..." -ForegroundColor Cyan
    Apply-ProgressPatch -JarPath $jarPath
}

# 6. Update settings
Write-Host ""
Write-Host "Configurando..." -ForegroundColor Cyan
$className = "com.github.deviceargent.$packageName.$($selected.Name)"
Update-Settings -CfgPath "$JdPath\cfg" -ClassName $className

# 7. Done
Write-Host ""
Write-Host "=== Instalacion completa ===" -ForegroundColor Green
Write-Host ""
Write-Host "Reinicia JDownloader para aplicar el tema." -ForegroundColor Cyan
Write-Host ""
