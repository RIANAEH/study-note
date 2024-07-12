# 공식문서를 읽으며 Spring 정리해보기

스프링의 핵심 기술로 IoC 컨테이너와 AOP를 이야기할 수 있습니다. 

## Spring IoC(Inversion of Control) Container

스프링의 IoC 컨테이너에서는 빈(Bean)의 생명주기를 관리해주고 빈을 생성하는 과정에서 의존성을 주입(DI, Dependency Injection)해줍니다. 빈의 생성 및 소멸이나 의존성을 주입해주는 코드를 개발자가 직접 작성하지 않고 스프링의 IoC 컨테이너에서 관리해주기 때문에 `제어의 역전`이라는 말을 사용하는 것입니다. 

> 빈(Bean): 스프링의 IoC 컨테이너에 의해 관리되는 객체로 POJO 형태를 따릅니다.

<p align="center">
    <img width="400px" src="https://user-images.githubusercontent.com/45311765/205502474-ed8c4cdf-7eaf-4146-a088-97e1a373a432.png">
</p>

스프링 IoC 컨테이너에서는 `Configuration Metadata`와 `POJO`를 이용해 실행 가능한 스프링 환경을 구성합니다. 개발자는 설멍 메타데이터를 통해 빈을 어떻게 관리할지 조작할 수 있습니다. 

개발자는 XML 또는 자바 기반의 `@Configuration`, `@Bean`, `@Import`, `@DependsOn` 등의 어노테이션을 활용해 설정 메타데이터를 구성할 수 있습니다. 

> SpringBoot에서는 편의성을 위해 `@Controller`, `@Service`, `@Repository` 등을 제공합니다. 

## Spring AOP

## Spring MVC

스프링 MVC는 서블릿 기반의 웹 프레임워크입니다. 다른 웹 프레임워크들과 마찬가지로 프론트 컨트롤러 패턴을 적용했으며 중앙 서블릿으로 DispatcherServlet을 제공합니다. 

WebApplicationInitializer 인터페이스를 상속한 클래스에서 DispatcherServlet을 생성하고 원하는 URL에 매핑할 수 있습니다. 

> 스프링 부트에서는 기본적으로 "/*"에 DispatcherServlet을 매핑하고 있습니다. 
