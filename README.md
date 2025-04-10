# 🎉 Welcome to AniScope - Your Anime Adventure! ✨

Hey! Meet **AniScope**, my Android app built with the [Jikan API](https://jikan.moe/). I had a blast creating this to explore top anime, watch trailers, and dig into details. Check it out! 🎬

---

## 🌟 What’s Inside? 🌈

### 1. Cool Walkthrough Screen 🎮
First time? You’ll see a fun welcome screen with a “Get Started” button to jump in—super inviting!

<img src="https://i.ibb.co/C3skmg3h/walkthrough.png" width="300" alt="Walkthrough Screen" style="display: block; margin: 0 auto;">
*That vibrant welcome sets the vibe!*

---

### 2. Anime List Screen 📺
The home screen’s where it gets good:
- Pulls top anime from `https://api.jikan.moe/v4/top/anime`.
- Shows title, episodes, rating, and posters.
- Pagination for smooth scrolling, plus pull-to-refresh.
The list API gives lots of details already—more can come from the detail API if needed!

<img src="https://i.ibb.co/b5cKMYN8/anime-list.png" width="300" alt="Anime List Screen" style="display: block; margin: 0 auto;">
*Love those posters and easy scrolling!*

---

### 3. Anime Detail Bottom Sheet 🎥
Tap an anime, and a slick bottom sheet pops up:
- Plays trailers (or shows the poster if none).
- Lists title, synopsis, genres, cast, episodes, and rating.
- Adds a “More Like These” section with random 5 picks.

<img src="https://i.ibb.co/bMnMWLj3/anime-details.png" width="300" alt="Detail Bottom Sheet" style="display: block; margin: 0 auto;">
*Check out that trailer and random suggestions!*

---

## 🎯 Assumptions 🌱
- The Jikan API would stay reliable.
- Posters work as a trailer backup.

## 🚧 Limitations ⚠️
- API rate limits might slow heavy scrolling.
- Some trailers might not play.

## 🛠️ How to Run 🏃‍♂️
1. Clone the repo: `git clone <your-repo-url>`
2. Open in Android Studio.
3. Sync and run on an emulator or device.

## 🌈 Final Thoughts 💭
AniScope was a joy to build! It’s got the basics covered with some neat extras. With more time, I’d add caching and a search bar. Let me know what you think—I’d love your feedback! 😄
