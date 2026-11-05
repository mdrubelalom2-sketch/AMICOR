AMICOR Android Project (প্রাথমিক ডিজাইন)

এই ZIP-এ AMICOR-এর Android Studio/Kotlin Jetpack Compose সোর্স প্রজেক্ট আছে।
Package name: com.amicor.dating

গুরুত্বপূর্ণ:
- এটি এখন UI prototype, পূর্ণ অ্যাপ নয়।
- Signup/Login বোতাম এখনো Firebase Authentication-এর সঙ্গে যুক্ত নয়।
- Chat, profile database, image upload, matching, report/block এখনো তৈরি হয়নি।
- Firebase-এর google-services.json এই ZIP-এ রাখা হয়নি। এটি ব্যক্তিগত কনফিগারেশন ফাইল; প্রকাশ্যে শেয়ার করবেন না।
- APK তৈরি করতে Android Studio/Gradle build environment লাগবে। শুধু ZIP ফাইল নিজে APK নয়।

পরবর্তী ধাপ:
1. প্রজেক্টটি Android Studio-তে খুলে Gradle Sync করুন।
2. প্রথমে UI build করে পরীক্ষা করুন।
3. এরপর Firebase Authentication ও Database নিরাপদভাবে সংযুক্ত করুন।
4. তারপর Profile, Like/Match, Chat এবং safety features তৈরি করুন।
