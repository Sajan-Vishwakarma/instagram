import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Post } from '../../../models/Post';
import { User } from '../../../models/User';
import { ImageService } from '../../../services/image.service';
import { LocalStorageService } from '../../../services/local-storage.service';
import { MediaService } from '../../../services/media.service';
import { PostService } from '../../../services/post.service';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrl: './create-post.component.css'
})
export class CreatePostComponent {
  successMessage !: string;
  errorMessage !: string;
  chosenImages !: any[];
  user !: User;

  postForm !: FormGroup;
  formData: FormData = new FormData();

  constructor(private formBuilder: FormBuilder, private imageService: ImageService,
    private localSS: LocalStorageService, private postService: PostService,
    private mediaService: MediaService, private router: Router) { }

  ngOnInit(): void {
    this.postForm = this.formBuilder.group({
      caption: [''],
      privacy: ['', [Validators.required]],
    })
    this.user = this.localSS.getItem('user');
  }

  urls !: any[]
  onFileSelected(event: any) {
    this.chosenImages = [];
    let files = event.target.files;
    if (files) {
      for (let file of files) {
        this.formData.append('images', file);
        let reader = new FileReader();
        reader.onload = (e: any) => {
          this.chosenImages.push(e.target.result);
        }
        reader.readAsDataURL(file);
      }
    }
  }

  submit() {
    if (!this.user.userId) return;

    console.log(this.postForm.value);
    const post: Post = {
      userId: this.user.userId,
      caption: this.postForm.value.caption,
      privacy: this.postForm.value.privacy,
    }

    this.imageService.uploadImages(this.formData)
      .subscribe(
        (imageIds) => {

          this.postService.createPost(post)
            .subscribe(
              (postId) => {

                this.mediaService.addMedia(postId, imageIds)
                  .subscribe(
                    (success) => {
                      this.successMessage = 'Post created successfully';
                      setTimeout(() => {
                        this.router.navigate(['/profile',this.user.username]);
                      }, 1500)
                    },
                    (err) => console.log('Error creating post, addMedia', err)
                  )
              },
              (err) => console.log('Error creating post, createPost', err)
            );
        },
        (err) => console.log('Err uploading multiple files')
      )
  }
}
