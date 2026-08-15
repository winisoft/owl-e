@echo off
setlocal EnableExtensions DisableDelayedExpansion

set "DB=owle_test"
set "USER=root"
set "PASSWORD=auta_i_lome1!"

set "DIR=D:\claude_access\owles_history\owle_sql_init\dev - Copy"
set "SCHEMA=%DIR%\dump-owle_test-202506151827.sql"

set "INITIALIZED=%DIR%\restore_v3.initialized"
set "DONE=%DIR%\restore_v3.done"
set "LOG=%DIR%\restore_v3.log"


rem ============================================================
rem FIRST RUN: CLEAN DATABASE + SCHEMA
rem ============================================================

if not exist "%INITIALIZED%" (
    echo.
    echo ============================================================
    echo INITIALIZING CLEAN owle_test DATABASE
    echo ============================================================
    echo.

    if exist "%DONE%" del "%DONE%"

    mysql -u "%USER%" -p"%PASSWORD%" ^
        -e "DROP DATABASE IF EXISTS owle_test; CREATE DATABASE owle_test;"

    if errorlevel 1 (
        echo.
        echo ============================================================
        echo FAILED TO DROP/CREATE DATABASE
        echo ============================================================
        exit /b 1
    )

    echo.
    echo ============================================================
    echo IMPORTING SCHEMA
    echo ============================================================
    echo.

    mysql -u "%USER%" -p"%PASSWORD%" "%DB%" < "%SCHEMA%"

    if errorlevel 1 (
        echo.
        echo ============================================================
        echo SCHEMA IMPORT FAILED
        echo ============================================================
        exit /b 1
    )

    echo initialized>"%INITIALIZED%"
    echo [%DATE% %TIME%] Database initialized>>"%LOG%"
)


rem ============================================================
rem DATA FILES
rem ============================================================

for %%F in ("%DIR%\*.sql") do (

    if /I not "%%~nxF"=="dump-owle_test-202506151827.sql" (

        findstr /X /C:"%%~nxF" "%DONE%" >nul 2>&1

        if errorlevel 1 (
            echo.
            echo ============================================================
            echo IMPORTING: %%~nxF
            echo ============================================================
            echo.

            echo [%DATE% %TIME%] START %%~nxF>>"%LOG%"

            python -c "import sys; src=open(sys.argv[1],'rb'); out=sys.stdout.buffer; [out.write(line.replace(b'owle_dev.',b'owle_test.')) for line in src]" "%%~fF" ^
            | mysql ^
                --init-command="SET SESSION FOREIGN_KEY_CHECKS=0; SET SESSION UNIQUE_CHECKS=0;" ^
                -u "%USER%" ^
                -p"%PASSWORD%" ^
                "%DB%" ^
                2>>"%LOG%"

            if errorlevel 1 (
                echo.
                echo ============================================================
                echo FAILED: %%~nxF
                echo ============================================================
                echo.
                echo [%DATE% %TIME%] FAILED %%~nxF>>"%LOG%"
                echo Error log:
                echo "%LOG%"
                exit /b 1
            )

            echo %%~nxF>>"%DONE%"
            echo [%DATE% %TIME%] OK %%~nxF>>"%LOG%"
            echo COMPLETED: %%~nxF
        ) else (
            echo Skipping completed file: %%~nxF
        )
    )
)

echo.
echo ============================================================
echo RESTORE COMPLETE
echo ============================================================

endlocal