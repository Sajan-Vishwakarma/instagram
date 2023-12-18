import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { validateUsername, validateName } from '../../../auth/form.validator';
import { ENVIRONMENT } from '../../../environments/environment';
import { User } from '../../../models/User';
import { ImageService } from '../../../services/image.service';
import { LocalStorageService } from '../../../services/local-storage.service';
import { SharedImageUserService } from '../../../services/shared-image-user.service';
import { UsersService } from '../../../services/users.service';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrl: './edit-user.component.css'
})
export class EditUserComponent {
  user !: User;
  editForm !: FormGroup;
  profileImageURL:any = ENVIRONMENT.DEF_PROFILE_PIC;
  selectedFile !: File;

  successMessage !: string;
  errorMessage !: string;

  constructor(private localSS: LocalStorageService, private formBuilder: FormBuilder,
    private imageService: ImageService, private usersService: UsersService,
    private router: Router, private siuService: SharedImageUserService) {
  }

  ngOnInit(): void {
    this.user = this.localSS.getItem('user');
    this.profileImageURL = this.localSS.getItem('PROFILE_URL');

    this.editForm = this.formBuilder.group({
      image: [''],
      username: [this.user.username, [Validators.required, validateUsername]],
      fullName: [this.user.fullName, [Validators.required, validateName]],
      bio: [this.user.bio]
    })
  }

  get fullName() {
    return this.editForm.controls['fullName'];
  }
  get username() {
    return this.editForm.controls['username'];
  }

  readURL(event: any) {
    if (event.target.files && event.target.files[0]) {
      this.selectedFile = <File>event.target.files[0];

      var reader = new FileReader();
      reader.onload = (event: ProgressEvent) => {
        this.profileImageURL = (<FileReader>event.target).result;
      }
      reader.readAsDataURL(event.target.files[0]);
    }
  }

  submit() {
    const userData: User = {
      userId: this.user.userId,
      emailId: this.user.emailId,
      password: this.user.password,
      username: this.editForm.value.username,
      fullName: this.editForm.value.fullName,
      bio: this.editForm.value.bio,
      profileImgId: this.user.profileImgId
    };

    this.successMessage = ''
    this.errorMessage = ''

    if (this.selectedFile)
      this.uploadImage(userData);
    else
      this.updateUser(userData);
  }

  uploadImage(userData: User) {
    const formData = new FormData();
    formData.append('image', this.selectedFile);
    this.imageService.uploadImage(formData)
      .subscribe(
        (imgID) => {
          userData.profileImgId = imgID;
          this.fetchImage(imgID);
          this.updateUser(userData);
        },
        (errImageRes) => console.log(errImageRes)
      )
  }

  fetchImage(imgID: number) {
    this.imageService.getImage(imgID)
      .subscribe(
        (image) => {
          this.siuService.profileImageSubject$.next(image.img_url);
          this.localSS.setItem('PROFILE_URL',image.img_url);
        }
      )
  }

  updateUser(userEditData: User) {
    this.usersService.updateUser(userEditData)
      .subscribe(
        (successMessage) => {
          this.successMessage = '' + successMessage.successMessage;
          this.localSS.setItem('user',userEditData);
          this.siuService.userSubject$.next(userEditData);
          setTimeout(() => {
            this.router.navigate(['/profile', userEditData.username]);
          }, 2000)
        },
        (errorMessage) => {
          this.errorMessage = errorMessage.error.errorMessage;
        }
      )
  }
}
