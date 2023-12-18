import { MediaInfo } from "./MediaInfo";
import { Post } from "./Post";
import { UserInfo } from "./UserInfo";

export interface PostInfo{
    postDTO: Post
    userInfoDTO: UserInfo
    mediaInfos: MediaInfo[];
}