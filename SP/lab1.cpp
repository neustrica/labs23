#include <windows.h>
#include <stdio.h>

// Функция для обработки сообщения WM_PAINT
void OnPaint(HWND hWnd)
{
    // Получить контекст устройства для окна
    HDC hdc = GetDC(hWnd);

    // Нарисовать бегущую строку
    TCHAR str[] = "Hello, world!";
    int x = 0;
    for (int i = 0; i < strlen(str); i++)
    {
        // Нарисовать символ
        TextOut(hdc, x, 0, str + i, 1);

        // Переместить курсор на следующую позицию
        x += 10;

        // Если курсор вышел за пределы окна, начать заново
        if (x > GetSystemMetrics(SM_CXSCREEN))
        {
            x = 0;
        }
    }

    // Отпустить контекст устройства
    ReleaseDC(hWnd, hdc);
}

// Функция для обработки сообщения WM_TIMER
void OnTimer(HWND hWnd, UINT uMsg, UINT_PTR idEvent, DWORD dwTime)
{
    // Обновить изображение окна
    InvalidateRect(hWnd, NULL, TRUE);
}

// Функция для обработки сообщения WM_COMMAND
void OnCommand(HWND hWnd, int id, HWND hWndCtl, UINT codeNotify)
{
    // Если выбран пункт меню Start
    if (id == ID_START)
    {
        // Запустить таймер
        SetTimer(hWnd, ID_TIMER, 100, NULL);
    }
    // Если выбран пункт меню Stop
    else if (id == ID_STOP)
    {
        // Остановить таймер
        KillTimer(hWnd, ID_TIMER);
    }
}

// Основная функция
int WINAPI WinMain(HINSTANCE hInstance, HINSTANCE hPrevInstance, LPSTR lpCmdLine, int nCmdShow)
{
    // Создать окно приложения
    HWND hWnd = CreateWindow(
        TEXT("MyApp"),
        TEXT("Бегущая строка"),
        WS_OVERLAPPEDWINDOW,
        CW_USEDEFAULT, CW_USEDEFAULT,
        300, 200,
        NULL, NULL, hInstance, NULL);

    // Если окно создано успешно
    if (hWnd != NULL)
    {
        // Зарегистрировать обработчики сообщений
        WNDCLASSEX wcex = { sizeof(WNDCLASSEX), CS_HREDRAW | CS_VREDRAW, WndProc, 0, 0, hInstance, NULL, NULL, NULL, NULL, TEXT("MyApp") };
        RegisterClassEx(&wcex);

        // Добавить пункты меню
        HMENU hMenu = CreateMenu();
        HMENU hSubMenu = CreateMenu();
        AppendMenu(hMenu, MF_POPUP, (UINT_PTR)hSubMenu, TEXT("Управление"));
        AppendMenu(hSubMenu, MF_STRING, ID_START, TEXT("Начать"));
        AppendMenu(hSubMenu, MF_STRING, ID_STOP, TEXT("Остановить"));
        SetMenu(hWnd, hMenu);

        // Показать окно
        ShowWindow(hWnd, nCmdShow);

        // Запустить цикл обработки сообщений
        while (GetMessage(&msg, NULL, 0, 0))
        {
            // Обработать сообщение
            TranslateMessage(&msg);
            DispatchMessage(&msg);
        }
    }

    // Завершить приложение
    return (int)msg.wParam;
}

// Функция обработки сообщений окна
LRESULT CALLBACK WndProc(HWND hWnd, UINT uMsg, WPARAM wParam, LPARAM lParam)

{
    switch (uMsg)
    {
    case WM_PAINT:
        OnPaint(hWnd);
        break;
    case WM_TIMER:
        OnTimer(hWnd, uMsg, wParam, lParam);
        break;
    case WM_COMMAND:
        OnCommand(hWnd, wParam, (HWND)lParam, 0);