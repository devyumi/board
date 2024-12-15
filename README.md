# 🌟 게시판 REST API 프로젝트
<p>게시글과 댓글을 작성할 수 있는 <strong>REST API 게시판 서비스</strong>입니다.</p>
<p>로그인 기능이 없는 대신 게시글(댓글) 등록시 비밀번호를 입력합니다.</p>

[<img src="https://img.shields.io/badge/velog 바로가기-20C997?style=for-the-badge&logo=velog&logoColor=white"/>](https://velog.io/@devyumi/개인-게시판-댓글-API)

<br>

## 🎨 주요 기능
<table>
  <tr>
      <td rowspan="2" align=center>게시글</td>
      <td align=center>CRUD</td>
      <td>R: 조회수, 검색(제목, 내용), 이미지 처리 기능 포함</td>
  </tr>
  <tr>
    <td align=center>신고</td>
    <td>게시글이 5번 이상 신고될 경우, 댓글과 파일이 모두 삭제되고 다른 테이블로 이관됨</td>
  </tr>
  <tr>
      <td rowspan="2" align=center>댓글</td>
      <td align=center>CRUD</td>
      <td align=center>-</td>
  </tr>
  <tr>
      <td align=center>추천</td>
      <td align=center>-</td>
  </tr>
</table>

<br>

## 📁 API
<p>작성한 API는 아래에서 확인할 수 있습니다.</p>

➡️ [API 바로보기](https://github.com/devyumi/board/blob/main/APIs.md)

<br>

## ⚙ 기술 스택
### ✔️ Back
<div>
<img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=Spring Boot&logoColor=white"/>
<img src="https://img.shields.io/badge/JPA-6DB33F?style=for-the-badge&logo=JPA&logoColor=white"/>
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
</div>

<br>

## ❗ 개선할 점
- 게시판 조회수 증가 시 중복을 방지하기 위해 Cookie를 사용할 것
- 댓글 정렬 조건을 '추천순', '최신순'으로 분리할 것

<br>

## 🛠️ Version
|버전|날짜|업데이트|
|:--:|:--:|:--:|
|v1.0|2024.01.29. ~ 2024.02.04.|CRUD 외 위에서 정의한 기능 구현|
|v2.0|2024.06.01.|이미지 처리 기능 추가|
