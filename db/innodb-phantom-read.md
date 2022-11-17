# InnoDB에서 Phantom Read를 막는 방법

InnoDB는 Repeatable Read를 기본 격리 수준으로 가져갑니다. 다른 DB와는 다르게 InnoDB의 Repeatable Read 레벨에서는 Phantom Read가 발생하지 않으며, 이는 언두 로그와 넥스트 키락을 통해 막아주고 있습니다. 

## 일반 SELECT

InnoDB는 일반 SELECT 상황에서는 락을 걸지 않습니다. 이는 언두 로그를 이용하기 때문에 가능합니다. 

## SELECT FOR UPDATE

SELECT FOR UPDATE에서는 데이터 변경을 대비해 락을 걸게 됩니다. 이때 InnoDB에서는 검색 및 인덱스 스캔 과정에서 일반 레코드 락이 아니라 `넥스트 키 락`을 사용해 팬텀 리드를 방지하고 있습니다. 

![IMG_6345](https://user-images.githubusercontent.com/45311765/202385117-ee06333a-a86f-4e68-bec4-7b495ae09bb8.jpg)

넥스트 키 락은 레코드 락과 갭 락을 혼합한 방식입니다. 

### Record Lock

### Gap Lock

### Next-Key Lock

## 참고 자료

- https://dev.mysql.com/doc/refman/8.0/en/innodb-next-key-locking.html
- https://dev.mysql.com/doc/refman/8.0/en/innodb-locking.html#innodb-next-key-locks
