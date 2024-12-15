# Board Service API

### POST 게시글 작성
```
/write
```

**Body** <span> form-data</span>
|KEY|VALUE|
|:--:|:--:|
|board|{ "nickname": "테스트01", "password": "xptmxm01!@", "title": "테스트 제목", "content": "테스트 테스트입니다."}|
|image|\<file>|

**Response**
```
{
    "nickname": "테스트01",
    "password": "xptmxm01!@",
    "title": "테스트 제목",
    "content": "테스트 테스트입니다."
}
```

<br>

#

<br>

### GET 게시글 조회
```
/
```

**Params**
|KEY|VALUE|
|:--:|:--:|
|page|1|
|keyword|안녕|

**Response**
```
[
    {
        "boardId": 37,
        "title": "안녕하세요.",
        "commentCount": 0,
        "nickname": "dltmddn555",
        "views": 13,
        "createDate": "2024-06-01"
    },
    {
        "boardId": 34,
        "title": "사진 없이 게시글",
        "commentCount": 0,
        "nickname": "사진12",
        "views": 1,
        "createDate": "2024-06-01"
    },
    {
        "boardId": 26,
        "title": "file 테스트",
        "commentCount": 0,
        "nickname": "사과사진1",
        "views": 0,
        "createDate": "2024-06-01"
    }
]
```

<br>

#

<br>

### GET 게시글 상세 조회
```
/{boardId}
```
**Response**
```
{
    "nickname": "5sport",
    "views": 3,
    "createDate": "2024.01.30. 21:05",
    "title": "이적 후 곧바로 뛸까?…",
    "content": "백승호(26)가 약 3년 만에 유럽에 재진출했다.",
    "images": [
            "originName": "apple.png",
            "saveName": "a1fc19e8a69c41889cdc0cc70a859965.png",
            "imagePath": "C:\\image\\20240601\\57de2f360ff246339090205edbeb965f.png",
            "imageSize": "2508",
    ]
    "comments": [
        {
            "nickname": "당9근",
            "content": "당근당근",
            "hearts": 3,
            "createDate": "2024.02.04. 18:00"
        },
        {
            "nickname": "당9근",
            "content": "바니바니",
            "hearts": 0,
            "createDate": "2024.02.04. 18:01"
        },
    ]
}
```

<br>

#

<br>

### PUT 게시글 수정
```
/{boardId}/edit
```

**Body** <span> form-data</span>
|KEY|VALUE|
|:--:|:--:|
|board|{{"password": "test!", "title" : "테스트01 수정", "content" : "내용 수정 내용 수정"}}|
|image|\<file>|

**Response**
```
{
    "password": "test!",
    "title" : "테스트01 수정",
    "content" : "내용 수정 내용 수정"
}
```

<br>

#

<br>

### PUT 게시글 삭제

```
/{boardId}
```

**Body**
```
{
    "password": "xptmxm723!@"
}
```

<br>

#

<br>

### POST 게시글 신고

```
/{boardId]/report
```

<br>

#

<br>

### POST 댓글 작성
```
/{boardId}/comment/write
```

**Body**
```
{
    "nickname": "테스트1234",
    "password": "test!",
    "content" : "ㅌㅇㄴ마ㅣㅇㄴㅁ랄"
}
```

<br>

#

<br>

### PUT 댓글 수정
```
/{boardId}/comment/{commentId}/edit
```

**Body**
```
{
    "password": "test!",
    "content": "댓글 수정87956"
}
```


<br>

#

<br>

### PUT 댓글 삭제
```
/{boardId}/comment/{commentId}
```

**Body**
```
{
    "password": "test!"
}
```

<br>

#

<br>

### POST 댓글 추천
```
/{boardId}/comment/{commentId}/heart
```
