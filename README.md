# Volunteer_Management_System_REST_API_Spring_Boot

## Steps to start app in test environment

1. RUN < [docker run -p 8080:8080 acetay/vms-springboot:v1.0.0](#) > in command line to start backend server on port 8080. (PLEASE DO THIS FIRST!)
2. RUN < [docker run -p 3001:3001 acetay/vmsfrontend:v2](#) > in command line to start backend server on port 3001. 
3. Mock credentials for testing:
- Admin - (username - "ace@mail.com", password - "password")
- Volunteer - (username - "coco@mail.com", password - "password")

## Description
### App Summary
1. A full-stack digital platform for new volunteer signups along with a CRM dashboard to enable staff to administer new programs and enrol new volunteers.
2. This is a pro-bono (proof-of-concept) project done to help a local charity organization to administer volunteers' signup, as well as manage its programs more efficiently. A simple process streamlining was performed to migrate some of the manual taskes commonly performed by staff to a digital platform.
3. Tech stacks - ReactJS with TypeScript, React Query, Tailwind CSS, Spring-Boot and Spring Security + Firebase Auth.
4. Deployment pipeline - GitHub, AWS code pipeline and AWS MySql RDS.
5. App in test environment (on port 8080) is using H2 in-memory database.
6. For frontend repository, please refer to https://github.com/acetay/vms_frontend_typescript

### Relational Diagrams
![My Image](RelationalDiagrams.png)

### Authentication & Authorization Flow between front-end and backend
1. Firebase Auth for authentication and authorization.
2. Setup Security Filter Chain and Firebase filter (in backend) to validate/verify all API requests, which require a JWT token.


![My Image](Auth_process.png)

### CICD Pipeline implemented
1. Github (Code Repository)
2. AWS Elastic Registry Service (Image Repository with vulnerability scan)
3. AWS Elastic Container Service - Fargate (with Load balancer and auto-scale)
4. AWS Paramerstore (Credential Management)
5. AWS CodePipeline (Github > CodeBuild > AWS Image Repo - ERS > Deploy)
6. AWS RDS MySQL (Database)

### Pipeline illustration 
image src: https://vtimd.medium.com/aws-codepipeline-the-good-and-the-bad-2bd607102c2
![My Image](DeploymentPipeline.png)


## Team Members

- [Jonathan](https://github.com/goodwill80 "jonathan's github")
- [Ace](https://github.com/acetay "ace's github")
- [Yingwang](https://github.com/shiywsg "yingwang's github")
- [Claire](https://github.com/clairetkw "claire's github")
