<#
  Demostración de las pruebas unitarias (Guía 1) para la sustentación.

  Uso, desde la carpeta del proyecto:
    powershell -ExecutionPolicy Bypass -File scripts\demo_sustentacion.ps1          # código actual (corregido): 33/33 APROBO
    powershell -ExecutionPolicy Bypass -File scripts\demo_sustentacion.ps1 -Antes   # código original: U-19 FALLO

  Con -Antes, el script pone TEMPORALMENTE la versión original de celularValido (Character.isDigit),
  ejecuta las pruebas y SIEMPRE restaura el archivo corregido al final, aunque algo falle.
#>
param([switch]$Antes)

$ErrorActionPreference = 'Stop'
$raiz = Split-Path -Parent $PSScriptRoot
Set-Location $raiz

$archivo = Join-Path $raiz 'src\main\java\co\edu\eam\agrodirecto\registro\ValidadorRegistro.java'
$respaldo = "$archivo.respaldo"
$pruebas = 'ValidadorRegistroUnitariasTest*,GestorPedidosLimitesTest'

function Ejecutar-Pruebas {
    Write-Host ""
    Write-Host "> mvn clean test -Dtest=`"$pruebas`"" -ForegroundColor Cyan
    Write-Host ""
    $salida = & mvn clean test "-Dtest=$pruebas" "-Dsurefire.failIfNoSpecifiedTests=false" 2>&1
    foreach ($linea in $salida) {
        $l = "$linea"
        if ($l -cmatch '^U-\d\d .*-> FALLO') { Write-Host $l -ForegroundColor Red }
        elseif ($l -cmatch '^U-\d\d ') { Write-Host $l -ForegroundColor Green }
        elseif ($l -cmatch '^\[INFO\] Running ') { Write-Host ($l -replace 'co\.edu\.eam\.agrodirecto\.', '') -ForegroundColor DarkGray }
        elseif ($l -cmatch '^\[ERROR\]   ') { Write-Host $l -ForegroundColor Red }
        elseif ($l -cmatch '^\[(INFO|ERROR)\] Tests run: \d+, Failures: \d+, Errors: \d+, Skipped: \d+$') {
            if ($l -cmatch 'Failures: 0, Errors: 0') { Write-Host $l -ForegroundColor Green } else { Write-Host $l -ForegroundColor Red }
        }
        elseif ($l -cmatch 'BUILD SUCCESS') { Write-Host $l -ForegroundColor Green }
        elseif ($l -cmatch 'BUILD FAILURE') { Write-Host $l -ForegroundColor Red }
    }
}

if (-not $Antes) {
    Write-Host "=== Pruebas unitarias con el codigo ACTUAL (corregido) ===" -ForegroundColor Yellow
    Ejecutar-Pruebas
    exit 0
}

Write-Host "=== Pruebas unitarias con el codigo ORIGINAL (Character.isDigit) ===" -ForegroundColor Yellow
Copy-Item $archivo $respaldo -Force
try {
    $utf8 = New-Object System.Text.UTF8Encoding($false)
    $texto = [System.IO.File]::ReadAllText($archivo, $utf8)
    $patron = "char c = celular\.charAt\(i\);\s*(//[^\n]*\n\s*)*if \(c < '0' \|\| c > '9'\) \{"
    $original = [regex]::Replace($texto, $patron, 'if (!Character.isDigit(celular.charAt(i))) {')
    if ($original -eq $texto) { throw "No se encontro la linea corregida en ValidadorRegistro.java; no se modifico nada." }
    [System.IO.File]::WriteAllText($archivo, $original, $utf8)
    Write-Host "(se puso temporalmente: if (!Character.isDigit(celular.charAt(i))) { ... )" -ForegroundColor DarkGray
    Ejecutar-Pruebas
}
finally {
    Copy-Item $respaldo $archivo -Force
    Remove-Item $respaldo -Force
    Write-Host ""
    Write-Host "Archivo ValidadorRegistro.java restaurado a la version corregida." -ForegroundColor Yellow
}
