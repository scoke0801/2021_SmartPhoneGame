# 2021 스마트폰 게임 프로그래밍 텀프로젝트 소개  

**1. 게임 컨셉**   
오래 전 오락실 게임인 프로거[FROGGER] 혹은 
비교적 최근의 모바일 게임인 길건너 친구들[Crossy Road]과 같은 게임의 모작을      
종스크롤 형식의 모바일 게임으로 제작하려 합니다.    

![image](https://user-images.githubusercontent.com/28253934/118914734-ba664780-b966-11eb-9e32-cdbaf0bf2972.png)  
  
 **2. 인게임 구현 상황**  
 화면에서 보이는 것처럼 플레이어 캐릭터와 다양한 종류의 차량들, 아이템,  
 물 지형과 통나무 장애물들을 구현하였고  
 이에 더해서 장애물과 충돌 시 이펙트가 생성되도록 하였습니다.
 ![image](https://user-images.githubusercontent.com/28253934/118914818-d7027f80-b966-11eb-90f3-28ecefe4639f.png)
 
**2. 내부 클래스**  
-구조    
게임 내에서 사용된 클래스들은 아래와 같고  
애니메이션을 위해 AnimationBitmap, GameBitmap클래스를 사용하였으며  
충돌처리를 위해 BoxCollidable 인터페이스를 구현하도록 하였고  
재활용이 필요한 차량 및 아이템 객체들에 대해서는 Recyclable 인터페이스를 구현하도록 하였습니다.  
플레이어의 이동과 관련된 내용에서 계산이 필요한 부분을 별도로 분리하여  
CalculateFunctions 클래스에서 구현하도록 하였습니다.  
각각 게임내의 주요 업데이트 로직은 개별 클래스에서 다루며,  
이는 아래의 핵심코드 이미지에 나와있습니다.  
![image](https://user-images.githubusercontent.com/28253934/118914861-eda8d680-b966-11eb-83ec-2dff01765ffb.png)
-상호작용  
개별 클래스들이 다른 클래스들과 상호작용하는 내용은 다음과 같습니다.    
플레이어와 아이템이 충돌 시, 아이템 종류에 따른 고유 효과가 적용되며  
플레이어와 차량 및 물 장애물들이 충돌 시 이펙트가 그려집니다.    
또한 플레이어가 플랫폼위에 위치하면, 플랫폼이 이동함에 따라서  
플레이어도 이동하도록 구현하였습니다.    
![image](https://user-images.githubusercontent.com/28253934/118914886-fac5c580-b966-11eb-9ce6-8a89f8034a08.png)  
-핵심코드1 
주요 클래스의 핵심코드는 아래와 같습니다.  
![image](https://user-images.githubusercontent.com/28253934/118914926-1204b300-b967-11eb-8f6e-f39a1eb46072.png)  
-핵심코드2  
![image](https://user-images.githubusercontent.com/28253934/118915006-2779dd00-b967-11eb-97f2-a973e39527c4.png)

**3. 개발 진척상황**  
개발하기로 계획하였던 사항들의 개발 진척도는 다음 표의 내용과 같습니다.
![image](https://user-images.githubusercontent.com/28253934/118915057-382a5300-b967-11eb-9452-ebea98ae995c.png)


**4. 커밋 기록**  
개발일정동안의 커밋 기록 및 주차별 커밋 기록은 다음 표의 내용과 같습니다.
![image](https://user-images.githubusercontent.com/28253934/118915136-614ae380-b967-11eb-87aa-2701eb5e45f1.png)
  
**5. 1차 발표 readme.md파일**  
1차 발표의 readme.me파일은 아래의 주소에서 확인 가능합니다.  
https://github.com/scoke0801/2021_SmartPhoneGame/blob/a28c368c3ac6c522f782d4eb088a2327d6121198/README.md
