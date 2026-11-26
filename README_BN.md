# Global Walkie Talkie — প্রাথমিক Android + Backend প্রজেক্ট

এটি একটি **শুরু করার মতো সোর্স-কোড ভিত্তি (starter project)**; সম্পূর্ণ উৎপাদন-প্রস্তুত বা প্রকাশযোগ্য অ্যাপ নয়।

## এখন কী আছে
- Android Kotlin + Jetpack Compose অ্যাপের প্রাথমিক UI
- বাংলা/ইংরেজি UI-এর জন্য string resources
- Private channel / member / invitation-এর ডেটা মডেল
- Node.js + Express backend-এর প্রাথমিক API skeleton
- পরবর্তী ধাপে LiveKit/WebRTC voice এবং Facebook Messenger share link যুক্ত করার নির্দেশনা

## গুরুত্বপূর্ণ সীমাবদ্ধতা
- এই সোর্সে এখনো বাস্তব লাইভ অডিও, OTP, Messenger-এর মাধ্যমে স্বয়ংক্রিয় Accept, production authentication, 1,000-user load-tested infrastructure বা Play Store signing নেই।
- Messenger কেবল invite link শেয়ার করবে। প্রাপক লিংক খুলে অ্যাপে login করে join request দেবেন; channel owner/admin অনুমোদন করলে সদস্য হবেন।
- ১,০০০ সদস্যের সক্ষমতা সার্ভার কনফিগারেশন ও বাস্তব load test ছাড়া নিশ্চিত করা যায় না।
- এই ZIP-এ APK নেই। APK/AAB বানাতে Android SDK/Gradle build environment লাগবে।

## Android ফোন দিয়ে কীভাবে এগোবেন
শুধু ফোন থাকলে Android Studio ব্যবহার করা যাবে না (এটি ডেস্কটপ সফটওয়্যার)। এই প্রজেক্ট GitHub-এ আপলোড করে GitHub Actions বা অন্য cloud build service দিয়ে build করানো সম্ভব; সেটআপের জন্য GitHub account ও signing key লাগবে।

## Build (কম্পিউটার/Cloud CI)
1. Android Studio-তে `android/` ফোল্ডার খুলুন।
2. Gradle sync করুন।
3. `Build > Build Bundle(s) / APK(s)` থেকে APK বা AAB তৈরি করুন।
4. Play Store-এর জন্য release signing এবং Play Console-এর testing/review সম্পন্ন করুন।

## Backend
`backend/` ফোল্ডারে:
- `GET /health`
- `POST /api/channels` — ডেমো চ্যানেল তৈরি
- `GET /api/channels/:id` — চ্যানেল তথ্য
- `POST /api/channels/:id/join-requests` — join request
- `POST /api/invitations` — invite link তৈরি (ডেমো)

**সতর্কতা:** API-গুলো starter/demo; production-এ authentication, authorization, database, rate limits, audit logs, HTTPS, abuse protection এবং secure token handling যোগ করতে হবে।
