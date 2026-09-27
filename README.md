# ✈️ Smart Travelogue

A travel-journal platform where travellers **write travelogues and share trip photos and videos**, and find places, hotels and packages, all from an **Android app**. A **Flask + MySQL** backend provides the REST API the app uses and an admin dashboard for managing content.

![Python](https://img.shields.io/badge/Python-3776AB?style=flat-square&logo=python&logoColor=white)
![Flask](https://img.shields.io/badge/Flask-000000?style=flat-square&logo=flask&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=mysql&logoColor=white)
![Android](https://img.shields.io/badge/Android-3DDC84?style=flat-square&logo=android&logoColor=white)
![Java](https://img.shields.io/badge/Java-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![Vercel](https://img.shields.io/badge/Vercel-000000?style=flat-square&logo=vercel&logoColor=white)

---

## 📸 Screenshots

| | |
|---|---|
| ![Home](docs/screenshots/home.jpg) | ![Travelogues](docs/screenshots/adminviewtravaloges.jpg) |
| **Landing page** | **Admin: all travellers' travelogues** |
| ![Travelogue photos](docs/screenshots/adminviewtravelogueimages.jpg) | ![Places](docs/screenshots/admin_manage_place.jpg) |
| **Admin: photos uploaded to a travelogue** | **Admin: places with map locations** |
| ![Hotels](docs/screenshots/admin_manage_hotels.jpg) | ![Packages](docs/screenshots/admin_manage_packages.jpg) |
| **Admin: hotels** | **Admin: hotel packages** |
| ![Users](docs/screenshots/admin_view_reg_user.jpg) | ![Notifications](docs/screenshots/admin_manage_ntification.jpg) |
| **Admin: registered travellers** | **Admin: push notifications to the app** |

<sub>Screenshots use the fictional Kerala demo data in <code>database/demo_data.sql</code>.</sub>

---

## ✨ Features

**Travellers (Android app)**
- 📝 Write, view and delete travelogues (place, title, story, date)
- 📸 Upload photos, videos and YouTube links to each travelogue, and manage your gallery
- 🌍 Read other travellers' travelogues, photos and videos
- 🏨 Browse places, hotels and hotel packages; save places you're interested in
- ⭐ Send feedback and complaints, and receive notifications

**Admin (web dashboard)**
- Manage place types, places (with map coordinates), hotels and packages
- View registered users, feedback, and every travelogue with its media
- Send notifications to all app users

**REST API**: 27 endpoints under `/api/*` that the Android app uses (login, registration, travelogues, uploads, hotels, feedback and more).

---

## 🏗️ Architecture

```
Android app (Java)  ──HTTP──▶  Flask REST API (/api/*)  ──▶  MySQL / SQLite
Admin dashboard (Jinja2) ──▶  Flask (/admin/*)          ──▶  uploaded media
```

```
.
├── web/                    # Flask backend + admin dashboard
│   ├── main.py             # app entry point
│   ├── api.py              # REST API for the Android app
│   ├── admin.py            # admin dashboard
│   ├── public.py           # landing page + login
│   ├── database.py         # MySQL or SQLite (demo mode)
│   ├── demo.sqlite         # demo database used on Vercel
│   ├── static/demo/        # demo photos
│   └── vercel.json
├── android/                # Android app (Java, Android Studio)
├── database/
│   ├── schema.sql          # MySQL schema
│   └── demo_data.sql       # fictional demo data
└── docs/screenshots/
```

---

## 🚀 Run it locally

```bash
# 1. Database
mysql -u root -p < database/schema.sql
mysql -u root -p smarttravalogue < database/demo_data.sql   # optional demo data

# 2. Backend
cd web
python -m venv .venv && source .venv/bin/activate
pip install -r requirements.txt
python main.py        # http://localhost:5008
```

Demo login for the admin dashboard is `admin / admin`. Traveller accounts for the app are `meera / demo123`, `daniel / demo123` and `priya / demo123`.

By default the app connects to MySQL on `localhost:3307` as `root` with no password. Override with `DB_HOST`, `DB_PORT`, `DB_USER`, `DB_PASSWORD`, `DB_NAME`.

> No MySQL? Run `DB_ENGINE=sqlite python main.py` to use the bundled demo database.

**Android app**: open `android/` in Android Studio and set the server IP to your machine's IP on port `5008`.

---

## ☁️ Deploy to Vercel

1. On [vercel.com/new](https://vercel.com/new), import this repository.
2. Set **Root Directory** to `web`.
3. Optionally add `SECRET_KEY` as an environment variable.
4. Click **Deploy**.

On Vercel the app runs in **demo mode**. It uses the bundled SQLite demo database (copied to `/tmp` at start-up) and stores uploads in `/tmp`, so it needs no external database and resets itself whenever the instance restarts. Set `DB_ENGINE=mysql` plus the `DB_*` variables to use a hosted MySQL instead.

---

## 👤 Author

**Aloke**, AI Engineer & Full-Stack Developer · Dublin, Ireland
[GitHub](https://github.com/alokekissac) · [LinkedIn](https://www.linkedin.com/in/alokekisssac/)

Built as my BCA main project at Girideepam Institute of Advanced Learning.
