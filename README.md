# E-title
## Setup backend

1. open project in intellij IDE
2. right click and run 'EtitleApplication'

## Setup frontend

1. cd e-title-frontend
2. npm install
3. npm start

## Important !!!
There are two implementation for CarService:
- @Qualifier("BlockchainCarServiceImpl"): Integrating with blockchain to register user
- @Qualifier("CarServiceImpl"): Don't integrating with bockchain 

## Known issues & Steps to resolve:
When you can't go to login page ( login page does not display).

Clear local storage:
- Right click and select Inspect
- Go to Application tab
- Clear local storage
