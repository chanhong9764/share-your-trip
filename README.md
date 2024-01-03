# 🚀 실시간 여행 일정 관리 플랫폼 

<br>
<p align="center"><img src="https://github.com/chanhong9764/shareyourtrip/assets/26763860/f546266c-6e40-437d-8a35-26b8bc3d0154" width="300" /></p>
<br>

## 🖥️ 프로젝트 소개
- 친구 혹은 지인들과 서로의 니즈를 실시간으로 공유하며 여행 일정을 계획하고 수정할 수 있도록 도와주는 플랫폼입니다.
- 방문했던 여행지 중에 인상 깊었던 곳을 다른 유저들과 공유하고 소통하여 여행 정보를 얻을 수 있습니다.
- [시연 영상]()
- [화면 설계서]()
- [DB ERD]()
<br>

## 🕰️ 프로젝트 기간
- 2023년 11월 09일 - 2023년 11월 23일
<br>

## 🧑‍🤝‍🧑 멤버구성
<div align="center">

| **박찬홍**(팀장) | **정인상**(팀원) |
| :------: |  :------: |
| [<img src="https://github.com/chanhong9764/shareyourtrip/assets/26763860/ccd07864-8e37-4b94-9952-5b053730cbb9" height=150 width=150> <br/> @chanhong9764](https://github.com/chanhong9764) | [<img src="https://github.com/chanhong9764/shareyourtrip/assets/26763860/4223f111-c72b-4687-a8c6-b32adda85d11" height=150 width=150> <br/> @JeongInsang](https://github.com/JeongInsang) |
| ![Static Badge](https://img.shields.io/badge/Backend-%23000000) ![Static Badge](https://img.shields.io/badge/Frontend-%23000000) | ![Static Badge](https://img.shields.io/badge/Backend-%23000000) ![Static Badge](https://img.shields.io/badge/Frontend-%23000000) |

</div>
<br>

## ⚙️ 개발 환경
| ***Setting Type*** | ***Properties*** |
| :------: |  :------: |
| Language | ![Static Badge](https://img.shields.io/badge/Java-007396?style=flat) ![Static Badge](https://img.shields.io/badge/Javascript-%23F7DF1E?style=flat) |
| IDE | ![Static Badge](https://img.shields.io/badge/IntelliJ%20IDEA-%23000000?style=flat) |
| Backend | ![Static Badge](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=flat) ![Static Badge](https://img.shields.io/badge/MySQL-%234479A1?style=flat) ![Static Badge](https://img.shields.io/badge/STOMP-%23000000?style=flat) ![Static Badge](https://img.shields.io/badge/Postman-%23FF6C37?style=flat) |
| Frontend | ![Static Badge](https://img.shields.io/badge/Vue.js-%234FC08D?style=flat) ![Static Badge](https://img.shields.io/badge/HTML5-%23E34F26) ![Static Badge](https://img.shields.io/badge/CSS3-%231572B6) ![Static Badge](https://img.shields.io/badge/Bootstrap-%237952B3?style=flat) ![Static Badge](https://img.shields.io/badge/Pinia-%23ffd859?style=flat) ![Static Badge](https://img.shields.io/badge/STOMP-%23000000?style=flat) |
| Communication | ![Static Badge](https://img.shields.io/badge/Figma-%23F24E1E) ![Static Badge](https://img.shields.io/badge/Mattermost-%230058CC) ![Static Badge](https://img.shields.io/badge/GitLab-%23FC6D26) |
<br>

## 📌 주요 기능
### 회원관리
---
#### 회원가입 [담당자 - 정인상]
- ID 중복 검사
- 이메일 인증을 통해 사용자 인증
  
#### 로그인 [담당자 - 정인상]
- 유저의 비밀번호는 SHA512를 이용하여 Hashing하고 Salt를 함께 저장하여 보안성을 강화
  
#### ID, PW 찾기 [담당자 - 정인상]
- 회원가입 시 입력했던 이메일에 인증 번호를 발송하여 사용자 인증
- 패스워드 정책 준수
  - 세가지 종류 이상의 문자구성으로 8자리 이상의 길이로 구성된 문자열
  - 두가지 종류 이상의 문자구성으로 10자리 이상의 길이로 구성된 문자열
      
#### 마이페이지 [담당자 - 박찬홍 / 정인상]
- 프로필 사진 변경
  
<br>

### 게시판 관리
---
#### 게시글 조회 [담당자 - 박찬홍]
- 10개씩 게시글을 조회하여 무한 스크롤로 구현
- 필터링(hashtag, 최신순 / 인기순) 기능 구현
- 가장 많이 작성된 hashtag 9개 상단에 출력
- 로그인 시, 로드되는 게시글에 추천을 눌렀는 지 여부를 확인 및 상태 변경

#### 게시글 상세 조회 [담당자 - 박찬홍]
- 게시글에 작성된 댓글 / 조회를 한 유저의 추천 여부 / 댓글 작성 여부를 함께 조회하여 상태 반영
- 조회한 유저가 작성자인 게시글이면 수정 / 삭제할 수 있는 셋팅 버튼 활성화
- 조회한 유저가 작성자인 댓글이면 삭제할 수 있는 셋팅 버튼 활성화
- 로그인 시, 댓글 작성 INPUT 활성화
- hashtag 클릭 시, 해당 필터링을 적용하여 게시글 조회
- 게시글 추천 / 추천 해제 기능 구현

#### 게시글 작성 [담당자 - 박찬홍]
- 내용 / Hashtag / 이미지 입력 가능
- 해시태그 최대 10개 설정
- 이미지 추가 시, 미리보기 이미지 구현

#### 게시글 수정 [담당자 - 박찬홍]
- 기존 게시글에서 삭제된 hashtag, 이미지들의 정보와 새로 추가된 hashtag, 이미지들을 함께 전송
- 기존의 데이터는 DB에서 삭제되고 새로운 데이터는 추가

<br>

### 여행 그룹 관리
---
#### 그룹 리스트 조회 [담당자 - 박찬홍]
- 조회한 유저가 참석한 여행 그룹 조회
- 각 그룹명 / 그룹 설명 / 참여자 프로필 정보가 출력되며, 참여자 프로필은 그룹 참여에 수락한 참여자들만 출력

#### 그룹 초대 [담당자 - 박찬홍]
- 초대할 유저의 아이디를 입력하면 Auto-Complete를 이용하여 출력
- 초대할 유저의 아이디를 클릭하면 해당 유저가 참여자로 등록되며 프로필 사진이 아래 추가
- 참여 유저 / 그룹명 / 그룹 설명 작성 후, 초대하기 버튼 활성화
- 초대하기 버튼 클릭 시, 해당 그룹 참여자들에게 알람으로 초대장 발송

#### 알람 [담당자 - 박찬홍]
- 그룹 초대장 수신 시, 알람 활성화
- 초대장 수신 시, 로그인이 되어 있다면 5초 동안 초대장 알람 표시하고 비로그인시 알람 박스 카운트 증가
- 알람 버튼 클릭 시, 응답하지 않은 모든 초대장 출력

<br>

### 여행 일정 관리
#### 채팅방 [담당자 - 박찬홍]
- 참여 유저들의 프로필 / ID / 채팅 정보를 출력
- 유저가 채팅방에 참여할 시, 채팅방 입장 알람을 참여자들에게 실시간 전송하여 3초간 활성화
- 본인이 작성한 채팅은 오른쪽에 표시, 왼쪽은 본인 외의 유저들의 채팅 표시

#### 여행 일정 [담당자 - 박찬홍 / 정인상]
- 카카오맵 API를 이용하여 지도 출력 / 키워드 검색 / 인포윈도우 커스텀
- 인포윈도우에서 유저들이 여행지를 추가 시, 왼쪽 여행 경로 탭에 순서대로 경유지 추가
- Drag & Drop을 통해 여행지 순서 변경 가능하며 실시간으로 그룹 참여자들에게도 반영
- 경로 찾기 버튼 클릭 시, 카카오맵 API를 이용하여 최적 경로를 받아와 출력
- 여행 경로 탭에 각 여행지에 대해 출발지 / 경유지 / 도착지 태그 표시
