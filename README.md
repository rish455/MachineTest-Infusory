3D Model Viewer

This project is a single-activity Android application that allows users to load and interact with multiple 3D GLB models independently on the same screen.

The application supports moving, resizing, rotating, zooming, displaying model part labels, and removing individual models while keeping the interaction modes clearly separated.

Tech Stack
Kotlin
Jetpack Compose
MVVM Architecture
StateFlow
SceneView
Google Filament
Minimum SDK: 24

3D Library
SceneView + Google Filament

I used SceneView for rendering the GLB models.

SceneView is built on top of Google Filament and provides convenient Android APIs for loading and displaying GLB/glTF models while still benefiting from Filament's high-performance rendering engine.

Why SceneView?

SceneView was selected because it provides:

Native GLB/glTF model support
Hardware-accelerated rendering
Filament-based rendering performance
Android lifecycle integration
Model transformation support
Camera and node APIs
Easier integration with Jetpack Compose
Lower implementation complexity compared with using raw Filament directly

Using SceneView allowed me to focus more on the assessment-specific requirements such as multiple model containers, gesture separation, model labels, and performance.

Architecture

The application follows an MVVM-style architecture similar to my existing Android project structure.

com.infusory.modelviewer

├── App.kt

├── data
│   ├── model
│   │   ├── Model3D.kt
│   │   ├── ModelInstanceState.kt
│   │   ├── ModelLabel.kt
│   │   └── ModelMode.kt
│   │
│   └── repository
│       └── ModelRepository.kt

├── renderer
│   └── LabelProjector.kt

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

└── utils
    └── GlbJsonParser.kt

Features
Multiple 3D Models

The user can add any of the five bundled GLB models using the Add Model button.

Multiple models can remain visible and interactive on screen simultaneously.

The application is designed to support at least five models at the same time.
