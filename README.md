# Country List App (Clean Architecture)

This application fetches a list of countries from a REST API and displays them in a `RecyclerView`.
It is built using **Kotlin** and follows the principles of **Clean Architecture**, ensuring a modular, testable, and maintainable codebase.

---

## Architecture Overview

The app uses **Clean Architecture** with the following layers:

```
 presentation   → ViewModel, UI (Activity, RecyclerView)
 domain         → UseCases, Repository, DTO
 data           → RepositoryImpl, Network & Mappers, DTO
 app            → WalmartApp
```

---

## API

Use Api to fetch country data:
```
https://gist.githubusercontent.com/peymano-wmt/32dcb892b06648910ddd40406e37fdab/raw/db25946fd77c5873b0303b858e861ce724e0dcd0/countries.json
```

---
## Tech Stack
- **MVVM Architecture with Clean code**
- **Kotlin**
- **Retrofit** – for API calls
- **Coroutines Flow** – asynchronous data flow
- **Unit Testing**
- **Recyclerview**
- **Connectivity Checker for Network Handling**
---

## Demo
<table>
  <tr>
    <th>Screenshot 1</th>
    <th>Screenshot 2</th>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/f6ea3589-b37a-48b5-ac66-4e43259ebcee" alt="Screenshot 1" width="400" /></td>
    <td><img src="https://github.com/user-attachments/assets/c86940df-65a3-4c1f-aed2-8673505b7e9f" alt="Screenshot 2" width="400" /></td>
  </tr>
</table>


https://github.com/user-attachments/assets/bd6b8757-d176-4a8d-a0a0-5f98eac4c4d8
