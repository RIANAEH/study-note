# RDB

> 면접에서 질문 받았는데.. 하나도 기억이 안 났다..🥲 그래서 다시 공부!

## 나쁜 테이블이란?

나쁜 테이블은 갱신 이상, 삭제 이상, 입력 이상의 세 가지 이상(anomaly) 현상을 가지기 때문입니다.

- 갱신 이상: 특정 속성 값을 update할 때 이에 의존적인 다른 속성들의 중복된 값들을 모두 update해야합니다. 
- 삭제 이상: 특정 속성 값을 delete했을 때 이와 연관된 다른 속성의 값까지 의도하지 않게 delete되는 문제가 발생합니다.
- 입력 이상: 특정 속성 값을 insert할 때 이와 연관된 다른 속성의 값까지 (필요없는데) insert해야하는 문제가 발생합니다. 

## 정규화를 통해 좋은 테이블로 만들자!


## 비 관계형 데이터 베이스란?

- 일반적으로 JOIN 연산을 지원하지 않는다.
    - 하나의 테이블에 모든 컬럼을 다 넣는다. 