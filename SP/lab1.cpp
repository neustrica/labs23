#include <windows.h>

#define TIMER_ID 1
#define TIMER_INTERVAL 100

LRESULT CALLBACK WndProc(HWND hWnd, UINT message, WPARAM wParam, LPARAM lParam);

int WINAPI WinMain(HINSTANCE hInstance, HINSTANCE hPrevInstance, LPSTR lpCmdLine, int nCmdShow)
{
    // Создать окно
    HWND hWnd = CreateWindow(
        "STATIC",
        "Бегущая строка",
        WS_OVERLAPPEDWINDOW,
        CW_USEDEFAULT, CW_USEDEFAULT,
        500, 500,
        NULL, NULL, hInstance, NULL);

    // Зарегистрировать обработчик сообщений
    SetWindowLongPtr(hWnd, GWLP_WNDPROC, (LONG_PTR)WndProc);
ч
    // Показать окно
    ShowWindow(hWnd, nCmdShow);

    // Запустить цикл обработки сообщений
    MSG msg;
    while (GetMessage(&msg, NULL, 0, 0))
    {
        TranslateMessage(&msg);
        DispatchMessage(&msg);
    }

    return msg.wParam;
}

LRESULT CALLBACK WndProc(HWND hWnd, UINT message, WPARAM wParam, LPARAM lParam)

{
    switch (message)
    {
        case WM_CREATE:
        {
            // Инициализировать флаг
            isRunning = false;

            // Создать объект меню
            HMENU hMenu, hSubMenu;

            hMenu = CreateMenu();
            AppendMenu(hMenu, MF_STRING, ID_RUN, "Run");

            hSubMenu = CreateMenu();
            AppendMenu(hSubMenu, MF_STRING, ID_START, "Start");
            AppendMenu(hSubMenu, MF_STRING, ID_STOP, "Stop");

            AppendMenu(hMenu, MF_POPUP, (UINT_PTR)hSubMenu, "Run");

            // Установить меню окна
            SetMenu(hWnd, hMenu);

            // Инициализировать положение надписи
            x = 0;
            y = 0;

            break;
        }
        case WM_COMMAND:
            if (wParam == ID_START)
            {
                // Зарегистрировать таймер
                SetTimer(hWnd, TIMER_ID, TIMER_INTERVAL, NULL);

                // Установить флаг
                isRunning = true;
            }
            else if (wParam == ID_STOP)
            {
                // Остановить таймер
                KillTimer(hWnd, TIMER_ID);

                // Установить флаг
                isRunning = false;
            }
            break;
        case WM_TIMER:
            if (isRunning)
            {
                // Вычислить новое положение надписи
                x = x + 1;

                // Обновить положение надписи в окне
                InvalidateRect(hWnd, NULL, TRUE);
            }
            break;
        case WM_PAINT:
        {
            // Получить контекст устройства
            HDC hdc = GetDC(hWnd);

            // Нарисовать надпись
            TextOut(hdc, x, y, "Бегущая строка", 13);

            // Отпустить контекст устройства
            ReleaseDC(hWnd, hdc);

            break;
        }
        case WM_DESTROY:
            PostQuitMessage(0);
            break;
    }

    return DefWindowProc(hWnd, message, wParam, lParam);
}

// Глобальные переменные
int x = 0, y = 0;
bool isRunning = false;