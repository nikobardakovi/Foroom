# Android 4 — Device Matrix

Test class: `com.example.foroom.tests.ConversationTests`
Build: current `debug` build (Foroom Training, `com.alternator.foroom.training`), installed on each device.
Date: 2026-10-08

## Configurations

| # | AVD name | Device profile | Android | API | Screen resolution | Density | Type |
|---|---|---|---|---|---|---|---|
| 1 | Small_Phone_API_33 | Small Phone | Android 13 | 33 | 720 × 1280 | 320 dpi | Emulator |
| 2 | Pixel_8 | Pixel 8 | Android 14 | 34 | 1080 × 2400 | 420 dpi | Emulator |
| 3 | Pixel_9_Pro_XL_API_35 | Pixel 9 Pro XL | Android 15 | 35 | 1344 × 2992 | 480 dpi | Emulator |

## Results

| Scenario | Android 13 (API 33) | Android 14 (API 34) | Android 15 (API 35) |
|---|---|---|---|
| 1 — Send "let's go for a drink" in johnWeek, verify after reopening<br>`userA_sendsDrinkInvitationInJohnWeek_andMessageRemainsAfterReopeningChat` | Pass | Pass | Pass |
| 2 — Ask about the favourite Automation Academy module in the full-name chat<br>`userA_asksAboutFavouriteAcademyModuleInFullNameChat` | Pass | Pass | Pass |
| 3 — User B swipes to User A's greeting and replies; User A sees the reply<br>`userB_readsOlderGreetingAndReplies_andUserA_seesReplyInSharedChat` | Pass | Pass | Pass |

## Runs performed on each configuration

1. Each scenario run on its own (three separate runs).
2. The full test class run.
3. The full test class run again, to check that earlier messages and saved sessions do not
   invalidate the assertions.

All runs passed on all three configurations.

## Test data

Prepared on each device by the `PreparedConversationData` rule before every test. Existing
accounts and chats are reused, so repeated runs work on the same device:

- User A: `academy_user_a`, User B: `academy_user_b` (fictional local accounts)
- Chats created by User A: `johnWeek`, `nikoloz bardakovi`, `something`
- Every sent message gets a unique timestamp suffix.

## Notes

- On the 720 × 1280 screen, the profile page does not scroll and the Sign Out item is partly
  hidden behind the bottom navigation bar. The first Android 13 run of scenario 3 failed
  because Espresso's `click()` requires 90% of a view to be visible. Signing out now taps the
  centre of the visible part of the item, as a user would.
- On Android 15, scenario 2 failed once in Android Studio: the chat card was tapped while the
  search was reloading the list, so the chat did not open. Opening a chat now repeats the tap
  until the conversation screen is shown.
- All results above were recorded with the final version of the code, including both fixes.
- Screenshots of the Android Studio test results are in `screenshots/android4/`.
