package com.example.Gift_buyer.parser;

import com.microsoft.playwright.*;

import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

public class parser {
    public static void loginAndSaveSession() {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            BrowserContext context = browser.newContext();
            Page page = context.newPage();

            page.navigate("https://web.telegram.org/k/");
            System.out.println("🔑 Войдите в Telegram вручную, затем нажмите Enter...");
            Thread.sleep(120000);

            context.storageState(new BrowserContext.StorageStateOptions().setPath(Paths.get("telegram-auth.json")));
            System.out.println("✅ Сессия сохранена!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void buyGiftAndReturnLink(String amount, String username) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            BrowserContext context = browser.newContext(
                    new Browser.NewContextOptions().setStorageStatePath(Paths.get("telegram-auth.json"))
            );
            Page page = context.newPage();
            page.navigate("https://web.telegram.org/k/#" + username);
            page.waitForSelector("div[aria-label='Message input']");
            page.click("button.btn-menu-toggle");
            page.waitForSelector("text=Отправить подарок");
            page.waitForSelector("div.popup-send-gift-category >> text=" + amount);
            page.click("div.popup-send-gift-category >> text=" + amount);


            page.waitForSelector("div._gridItem_1shbt_20");

            List<ElementHandle> availableGifts = page.querySelectorAll("div._gridItem_1shbt_20:not(:has(div._badgeSoldout_1shbt_104))");

            if (availableGifts.isEmpty()) {
                System.out.println("⚠️ Нет доступных подарков (все проданы).");
                return;
            }


            int randomIndex = new Random().nextInt(availableGifts.size());
            ElementHandle gift = availableGifts.get(randomIndex);
            gift.click();

            page.waitForSelector("button:has-text('OK')");
            page.click("button:has-text('OK')");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}