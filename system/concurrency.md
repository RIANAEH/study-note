# 동시성 제어하기

동시성을 제어할 수 있는 방법은 여러가지가 있으며, 이 방법들을 서로 조합해 사용할 수도 있습니다. 
따라서 **서비스의 인프라 구조와 비즈니스 로직에 맞는 방법**을 선택해야 합니다. 

> ✅ **동시성 제어 방법**  
> 1. synchronized
> 2. 격리 레벨 제어
> 3. 낙관적 락
> 4. 비관적 락
> 5. 분산락

## synchronized (자바 사용시!)

임계 영역에 접근하는 메서드가 여러 개인 경우, 또는 서버가 여러 대인 경우에는 synchronized 키워드로는 동시성을 제어할 수 없습니다. 

## 낙관적 락 (Optimistic Lock)

낙관적 락은 이름 그대로 트랜잭션 대부분은 충돌이 발생하지 않는다고 낙관적으로 가정하는 방법입니다. 
데이터베이스 락을 사용하지 않고 애플리케이션 락을 사용해 처리합니다. 
JPA가 제공하는 버전 관리 기능을 이용해 구현할 수 있습니다. 
낙관적 락은 트랜잭션을 커밋하기 전까지는 트랜잭션의 충돌을 알 수 없다는 특징을 가집니다. 

만약 한명의 사용자에 대한 3개의 결제 요청이 동시에 들어오면 낙관적 락을 사용하는 경우 2개의 결제 요청은 롤백이 됩니다. 
하지만 사용자 입장에서는 **순차적으로 3개의 요청이 모두 성공**하기를 바라고 있을 것입니다. 
낙관적 락을 이용하는 경우 롤백 말고 다른 엑션을 위해서는 충돌이 발생하는 상황에 대한 처리를 직접 해줘야하는데 생각보다 쉽지 않은 작업이라고 합니다. 

```
org.hibernate.StaleStateException: Batch update returned unexpected row count from update [0]; actual row count: 0; expected: 1; statement executed: update order_table set created_at=?, menu_id=?, menu_name=?, menu_price=?, status=?, user_id=?, version=? where id=? and version=?
```

## 비관적 락

비관적 락은 트랜잭션의 충돌이 발생한다고 가정하고 우선 락을 걸고 보는 방법입니다. 
데이터베이스 락을 사용합니다. (ex. SELECT FOR UPDATE)

```sql
select
    point0_.id as id1_4_,
    point0_.amount as amount2_4_,
    point0_.updated_at as updated_3_4_,
    point0_.user_id as user_id4_4_ 
from
    point point0_ 
where
    point0_.user_id=? for update
```

### 트랜잭션 문제

```
TransactionRequiredException: no transaction is in progress
```

- https://woonys.tistory.com/entry/Transactional%EC%9D%80-%EB%A7%8C%EB%8A%A5%EC%9D%B4-%EC%95%84%EB%8B%99%EB%8B%88%EB%8B%A4-2-%ED%8A%B8%EB%9E%9C%EC%9E%AD%EC%85%98%EC%9D%98-%EA%B2%A9%EB%A6%AC%EC%84%B1%EA%B3%BC-lock
- https://junhyunny.github.io/spring-boot/jpa/junit/jpa-pessimitic-lock/

## 분산 락

- https://techblog.woowahan.com/2631/

