export interface User{
    userId?:number,
    username:string,
    password:string,
    emailId:string,
    fullName:string,
    bio?:string,
    profileImgId?:number,
    createdAt?:Date
}