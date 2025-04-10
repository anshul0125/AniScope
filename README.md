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

<img src="https://media-hosting.imagekit.io/a6f31b8282db405e/anime_list.png?Expires=1838927731&Key-Pair-Id=K2ZIVPTIP2VGHC&Signature=Ei5lu3AiGcX7v~YCBlKdSKqHLpGQO2FdKOm0gtgUeldE6MdwCk9FmuwEAaPst6w26rJGGdwCXxoPJpPJelmCWLecSrLU1l7eo3Tp46PYI~DLnc-4v2Qa3JB8HDh9RyVb53dDI8Ooc2UTuiqD0QK7Kg8sq5fQHfzoEZ8y5ClakmWDdVUzEUrVipXNg7PohpjGPZu2JAy~uJhvu-DXnCo2WeEsDQdnNtku7w3sw6KjY4tqHKiIpGj60igV7biUdbj7o1k4jR77cscH5R8pRAFpbSCQIm5ZWPRt0~e5JAkDz42-ks7Pd-kEhkM81P5B3xa0Ty1Y5-~zCj~M-qpJ~qwL4w__" width="300" alt="Anime List Screen" style="display: block; margin: 0 auto;">
*Love those posters and easy scrolling!*

---

### 3. Anime Detail Bottom Sheet 🎥
Tap an anime, and a slick bottom sheet pops up:
- Plays trailers (or shows the poster if none).
- Lists title, synopsis, genres, cast, episodes, and rating.
- Adds a “More Like These” section with random 5 picks.

<img src="https://media-hosting.imagekit.io/78572a16b1754e9f/anime_details.png?Expires=1838927839&Key-Pair-Id=K2ZIVPTIP2VGHC&Signature=LAFkXh6vVd0z7EPQ2Y2BQ1388~pbdVXghWP~S~Yq3zNWs1vj-Lfmw4RP3FvfgJ5PI4Slt4wbfvPMlfLJ8PDDAFoh1rav-4jbNYLo56keku7K~ARMVAsw79fgfZg8XkC3umivQZlHcFMSVtQK-e1-7uy5dJ~By9Mi6rTMEfunxXdjdwgY8TIxwcGEtlTOHnLrOb1JU4ImFslI52yG3DrUFClLCqVcVMoumu1PKdiVMhPIhPbFMkk8MnuFR42v7rA1Qk6nL-QU8dOF7~fu3VWsK45DVwomzvcLOL3x~hRGmPZ6t7sksH0OrimQ~tIvDLAVNN0ztvxZesJsWP3Bn3ZUVA__" width="300" alt="Detail Bottom Sheet" style="display: block; margin: 0 auto;">
*Check out that trailer and random suggestions!*

---

## 🎯 Assumptions 🌱
- The Jikan API would stay reliable.
- Posters work as a trailer backup.

## 🚧 Limitations ⚠️
- API rate limits might slow heavy scrolling.
- Some trailers might not play.

## 🛠️ How to Run 🏃‍♂️
1. Clone the repo: `git clone <https://github.com/anshul0125/AniScope.git>`
2. Open in Android Studio.
3. Sync and run on an emulator or device.

## 🌈 Final Thoughts 💭
AniScope was a joy to build! It’s got the basics covered with some neat extras. With more time, I’d add caching and a search bar. Let me know what you think—I’d love your feedback! 😄
