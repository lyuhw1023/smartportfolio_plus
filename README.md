# Smart Portfolio (업데이트 버전)
개발자의 포트폴리오를 앱으로 구현한 Android 포트폴리오 앱

<img width="100" alt="splash" src="https://github.com/user-attachments/assets/75d01b45-b9ce-4ad1-bbdb-49e8cfcc039a" />

<br><br>


## 🔗 업데이트 개요 (2025.05 기준)
> 이 프로젝트는 기존 SmartPortfolio 프로젝트를 개선하여 코드 구조를 최적화하고, JetPack Compose 및 MVVM 패턴을 적용하여 유지보수성을 향상시킨 버전입니다.

[이전 프로젝트 보러가기](https://github.com/lyuhw1023/smart_portfolio)

<br>

## ✅ 주요 업데이트 내용

- Jetpack Compose 도입: 선언형 UI로 전환하여 코드 간결화 및 UI 일관성 향상
- MVVM 패턴 적용: 역할 분리를 통해 로직과 UI를 체계적으로 관리
- Room + Firebase 연동: 오프라인/온라인 데이터 동기화 구조로 확장성 확보
- Firebase Auth 기반 사용자 권한 제어: 개발자/회원/비회원 권한 분기 처리
- UI/UX 개선: Custom Appbar 및 Card 등을 적용하여 재사용성을 높이고 UI 구조 최적화

<br>

## 🚀 프로젝트 개요
Smart Portfolio는 Android 개발자가 자신의 이력과 프로젝트를 효과적으로 정리하고 보여줄 수 있도록 만든 포트폴리오 앱입니다. 사용자는 앱을 통해 프로젝트, 수업 이력, 자기소개, 메시지 등을 확인하거나 작성할 수 있으며, 개발자는 관리 권한으로 내용을 수정/삭제할 수 있습니다.
기존의 단순 로컬 앱을 벗어나, Firebase 연동 및 사용자 권한 분기를 통해 실질적인 사용자 관리와 앱 운영 시나리오를 반영하였습니다.

<br>

## 🔥 업데이트 효과

1️⃣ Jetpack Compose + MVVM 도입

- XML → Compose 전환으로 유지보수성 및 일관된 UI 구현
- ViewModel 기반 상태 관리로 화면 간 데이터 흐름 명확화
- UI, 로직, 데이터 계층 분리로 기능 확장 용이

<br>

2️⃣ Room + Firestore 연동

- 로컬(Room) 저장소와 클라우드(Firestore)를 함께 사용하여 오프라인 동기화 시나리오 구현
- Firestore의 문서 ID와 Room의 localId 동기화 → 정확한 업데이트 및 삭제 처리
- 개발자용은 Firestore 기반 CRUD, 사용자용은 제한된 등록/수정 권한 적용

<br>

3️⃣ Firebase Auth 기반 권한 제어

- 관리자 Firebase UID 기반 권한 분기
- 사용자 역할에 따라 메시지 등록/수정/삭제 여부 제어
- 로그인 상태에 따라 화면 기능 동적 분기 처리

<br>

4️⃣ UI/UX 개선

- Custom Appbar 및 Card 적용 → 일관된 스타일 및 가독성 향상

<br><br>

## 📌 프로젝트 주요 기능
1. 로그인 및 권한 분기  
> Firebase Auth를 기반으로 로그인한 사용자의 UID에 따라 개발자/회원/비회원 권한 구분   

<img src="https://github.com/user-attachments/assets/2dd33804-d60f-415d-8246-3d9272b91c94" width="200px"/>
<img src="https://github.com/user-attachments/assets/5a9c0c64-ecae-4053-b04b-aa04b5cf8293" width="200px"/>

<br><br>

2. Project 관리 기능  
> 개발자의 프로젝트 리스트를 카드 형식으로 표시하고, 깃허브 외부 링크로 연결  
> Firestore에 저장된 프로젝트 정보를 불러오며, Room과 병행하여 동기화  
> 개발자는 등록/수정/삭제/조회 가능  
> 회원/비회원은 조회 가능  
> 순서대로 개발자 화면, 회원/비회원 화면, 프로젝트 추가 화면  

<img src="https://github.com/user-attachments/assets/b01c2811-0464-4e67-a9b1-2667a33b7655" width="200px"/>
<img src="https://github.com/user-attachments/assets/1eaf37a6-0004-432e-9b45-739a7da91b62" width="200px"/>
<img src="https://github.com/user-attachments/assets/43bf6106-66b1-42d9-95b2-27f57ca1d216" width="200px"/>

<br><br>


3. Contact 기능  
> 메시지를 남기고 읽을 수 있는 기능 제공  
> 개발자는 전체 메시지 조회 가능   
> 사용자는 본인 메시지에 한해 등록/수정/삭제/조회 가능   
> 순서대로 개발자 화면, 회원 화면, 컨택트 추가 화면  

<img src="https://github.com/user-attachments/assets/0d5838a3-8f65-46d7-8b0d-d3e6a63be84f" width="200px"/>
<img src="https://github.com/user-attachments/assets/5c04c841-5b45-4055-860a-a4a5d7e0af69" width="200px"/>
<img src="https://github.com/user-attachments/assets/86cd3480-8e32-42a8-80d9-b091e790ad16" width="200px"/>

<br><br>

4. 사용자 중심 UI 흐름  
> 네비게이션바, CustomAppBar 등으로 사용자 경험 최적화  
> 각 화면은 상태별로 명확히 구분되어 있고, 필요한 정보만 표시  
> 순서대로 Navigation, Home, About, More 화면  

<img src="https://github.com/user-attachments/assets/aaa50bc8-36a0-4729-a724-fe255072d368" width="200px"/>
<img src="https://github.com/user-attachments/assets/9a010888-7296-43a0-b7e4-eca9dc1dd56c" width="200px"/>
<img src="https://github.com/user-attachments/assets/75371663-dbfb-4641-8ac0-d9bf4b944da8" width="200px"/>
<img src="https://github.com/user-attachments/assets/35f812c1-9d86-4f6f-b673-770133ba9fe6" width="200px"/>

<br><br>

## 📌 프로젝트 비교

|항목|기존 프로젝트|개선된 프로젝트|
|:---:|:---:|:---:|
|UI 구조|Kotlin + XML 기반|Jetpack Compose 기반 선언형 UI|
|상태 관리|단순 setState, 구조 혼합|MVVM 패턴으로 분리|
|저장소|SQLite 단독 사용|Room + Firebase 병행 구조|
|사용자 권한|없음|Firebase Auth 기반 권한 제어|
