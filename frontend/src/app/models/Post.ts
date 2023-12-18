export interface Post {
    postId ?: number;
    userId: number;
    caption: string;
    privacy: number;
    createdAt?: Date;
}