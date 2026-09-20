# 3D Model Viewer

A **single-activity Android application** that allows users to load and interact with multiple **3D GLB models independently on the same screen**.

The application supports:

* Loading multiple 3D models
* Moving models around the screen
* Resizing individual model containers
* Rotating models
* Two-finger zoom in / zoom out
* Displaying model part labels
* Removing individual models
* Independent interaction for each model

---

## Tech Stack

* **Kotlin**
* **Jetpack Compose**
* **MVVM Architecture**
* **StateFlow**
* **SceneView**
* **Google Filament**
* **Minimum SDK:** 24

---

## 3D Rendering Library

### SceneView + Google Filament

The application uses **SceneView** to render GLB models.

SceneView is built on top of **Google Filament** and provides convenient Android APIs for loading and displaying **GLB/glTF models**, while still benefiting from Filament's high-performance rendering engine.

### Why SceneView?

SceneView was selected because it provides:

* Native GLB/glTF model support
* Hardware-accelerated 3D rendering
* Google Filament-based rendering performance
* Android lifecycle integration
* Model transformation support
* Camera and node APIs
* Easy integration with Jetpack Compose
* Lower implementation complexity compared with using raw Filament directly


---

## Architecture

The application follows an **MVVM-style architecture**, structured similarly to a production Android application.

```text
app
├── App.kt
│
├── data
│   ├── model
│   │   ├── Model3D.kt
│   │   ├── ModelInstanceState.kt
│   │   ├── ModelLabel.kt
│   │   └── ModelMode.kt
│   │
│   └── repository
│       └── ModelRepository.kt
│
├── renderer
│   └── LabelProjector.kt
│
├── ui
│   ├── MainActivity.kt
│   ├── MainViewModel.kt
│   ├── MainViewModelFactory.kt
│   │
│   └── viewer
│       ├── ModelViewerScreen.kt
│       ├── ModelContainer.kt
│       ├── ModelScene.kt
│       ├── ModelControls.kt
│       ├── ModelPickerDialog.kt
│       └── ModelLabelOverlay.kt
│
└── utils
    └── GlbJsonParser.kt
```

---

## Features

### Multiple 3D Models

The user can add any of the bundled GLB models using the **Add** button.

Multiple models can remain visible and interactive on the screen simultaneously.

The application is designed to support **at least five 3D models at the same time**.

---

## Usage

1. Launch the application.
2. Tap the **Add** button.
3. Select one of the available GLB models.
4. Repeat the process to add multiple models.
5. Move or resize each model container independently.
6. Rotate the 3D model using touch gestures.
7. Use two fingers to zoom the model in or out.
8. Enable model labels when required.
9. Use the remove control to delete an individual model.

---

## Conclusion

This project demonstrates an Android implementation for displaying and independently manipulating multiple 3D GLB models using **Kotlin, Jetpack Compose, SceneView, and Google Filament**.

The architecture keeps rendering, model state, business logic, and UI responsibilities separated while providing a flexible base for adding additional 3D interaction features in the future.
