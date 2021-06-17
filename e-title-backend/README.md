# e-title-backend
## Setup backend
1. git clone https://github.com/dangersvn/e-title-backend.git
2. open project in intellij IDE
3. right click and run 'EtitleApplication'

## Setup frontend
1. git clone https://github.com/dangersvn/e-title-frontend.git
2. cd e-title-frontend
3. npm install
3. npm start

## Important !!!
Blockchain integration:
There are two implementation for UserService:
- @Qualifier("BlockchainUserServiceImpl"): Integrating with blockchain to register user
- @Qualifier("UserServiceImpl"): Don't integrating with bockchain 
There are two implementation for CarService:
- @Qualifier("BlockchainCarServiceImpl"): Integrating with blockchain to register user
- @Qualifier("CarServiceImpl"): Don't integrating with bockchain 

## Known issues & Steps to resolve:
When you can't go to login page ( login page does not display).

Clear local storage:
- Right click and select Inspect
- Go to Application tab
- Clear local storage

## Initialize data
The webapplication is initialized two users:

Normal user: 
- username: viet@gmail.com
- password: 123

Organization user:
- username: haidang063@gmail.com
- password: 123

When the normal user logged-in there are two cars will display at my cars page
Organization user can register more cars, changing car title  