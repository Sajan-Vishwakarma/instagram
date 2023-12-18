export interface Comments{
    cid?: number;
    postId: number;
    userId: number;
    comment: string;
    createdAt ?: Date;
}