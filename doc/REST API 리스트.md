## REST API 리스트

### User

| HTTP Verb | URL 패턴                          | 설명                                                         |
| --------- | --------------------------------- | ------------------------------------------------------------ |
| POST      | /users/signin                     | 유저 로그인                                                  |
| POST      | /users/signup                     | 유저 회원가입                                                |
| GET       | /users/{user_id}                  | 유저 개인 정보 조회                                          |
| GET       | /users/{user_id}/mypage           | 유저 마이페이지                                              |
| PUT       | /users/{user_id}                  | 유저 개인 정보 수정                                          |
| PUT       | /users/{user_id}/mypage           | 유저 마이페이지 수정                                         |
| DELETE    | /users/{user_id}                  | 유저 정보 삭제                                               |
| GET       | /users/{user_id}/foods            | 유저 식자재 조회                                             |
| POST      | /users/{user_id}/foods            | 유저 식자재 추가                                             |
| DELETE    | /users/{user_id}/foods            | 유저 식자재 삭제                                             |
| GET       | /users/{user_id}/follow           | 해당 유저의 팔로워/팔로잉 조회     <br />{<br />    follow: true      <br />    following : false     <br />} |
| POST      | /users/{user_id}/follow           |                                                              |
| DELETE    | /users/{user_id}/follow           | {<br />       follow: {user_id}     <br />}                  |
| GET       | /users/{user_id}/steps            |                                                              |
| GET       | /users/{user_id}/steps/{tag_name} |                                                              |
| POST      | /users/{user_id}/steps            |                                                              |
| GET       | /users/{user_id}/process          |                                                              |

### Board

| HTTP Verb | URL 패턴                                 | 설명                                                     |
| --------- | ---------------------------------------- | -------------------------------------------------------- |
| GET       | /boards                                  | 전체 레시피 목록 조회 페이지                             |
| POST      | /boards/create                           | 신규 레시피 생성 및 form 표시                            |
| GET       | /boards/{board_pk}                       | 단일 레시피 조회와 댓글 전체 조회 및 댓글 작성 form 표시 |
| PUT       | /boards/{board_pk}                       | 기존 레시피 수정 및 form 표시                            |
| DELETE    | /boards/{board_pk}                       | 기존 레시피 삭제                                         |
| POST      | /boards/{board_pk}/comments              | 신규 댓글 생성                                           |
| DELETE    | /boards/{board_pk}/comments/{comment_pk} | 기존 댓글 삭제                                           |
| PUT       | /boards/{board_pk}/comments/{comment_pk} | 기존 댓글 수정                                           |