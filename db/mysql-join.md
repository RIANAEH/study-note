# MySQL에서는 JOIN을 어떻게 처리할까?

데이터베이스의 옵티마이저(optimizer)는 동일한 표현 및 연산 방식에서 가장 최적의 수행방식을 찾아 실행 계획(explain plan)을 수립합니다. 

## 조인 알고리즘의 종류

- Nested-Loop Join
- Block Nested-Loop Join
- Indexed Nested-Loop Join
- Hash Jion
- Merge Join

데이터베이스에서 조인 연상을 수행하는 알고리즘은 여러가지가 있으며, 각 RDBMS에서 지원하는 알고리즘이 다릅니다.

MySQL을 예로 설명해보면, MySQL에서는 8.0.18 버전 전까지 블록 중첩 루프 조인만을 지원했으며, 8.0.18 버전 이후로는 해시 조인을 추가적으로 지원하고 이를 기본으로 채택하고 있습니다.  

## Nested-Loop Join

중첩 루프 조인은 가장 단순한 조인 알고리즘으로, 각 행의 모든 조합에 대하여 조건을 비교하는 방법입니다. 

중첩 조인은 블록 단위로 디스크 I/O가 이루어지는 방식을 고려하지 않아 연산 비용이 비쌉니다. 보통의 경우에는 블록 중첩 루프 조인을 사용합니다. 

## Block Nested-Loop Join

> 아 왤케 이해가 안 가지.. 담에 다시 볼래..🥲

## Hash Jion 

해시 조인은 주어진 조인 테이블을 해시 함수를 사용하여 다수개의 파티션으로 분할하고 동등한 해시 값을 가지는 파티션 간에만 조인 연산을 수행합니다. 

동등 조인, 자연 조인에서만 적용 가능합니다. 

## MySQL에서는 어떤 조인 알고리즘을 사용할까?

MySQL 8.0에서는 가능한 경우 무조건 `해시 조인(Hash Join)`을 사용합니다. 해시 조인 적용이 불가능한 경우에는 `블록 중첩 루프 조인(Block Nested-Loop Join)`을 사용합니다. 이전 버전에서는 블록 중첩 루프 조인을 기본으로 사용했습니다. 

```sql
CREATE TABLE t1 (c1 INT, c2 INT);
CREATE TABLE t2 (c1 INT, c2 INT);
CREATE TABLE t3 (c1 INT, c2 INT);
```

<p align="center">
    <img width="600px" src="https://user-images.githubusercontent.com/45311765/205493244-fdb73aef-3cb7-47ee-8ef8-96e59d2ea044.png">
</p>

<p align="center">
    <img width="600px" src="https://user-images.githubusercontent.com/45311765/205493595-18c164a7-9f94-40dc-8d72-250481caa8b1.png">
</p>

## 참고 자료

- 이상호 교수님의 데이터베이스2 
- [MySQL 공식 문서: Hash Join Optimization](https://dev.mysql.com/doc/refman/8.0/en/hash-joins.html)
- [MySQL 공식 문서: Hash join in MySQL 8](https://dev.mysql.com/blog-archive/hash-join-in-mysql-8/)
