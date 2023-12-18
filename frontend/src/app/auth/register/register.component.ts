import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from '../../models/User';
import { LocalStorageService } from '../../services/local-storage.service';
import { UsersService } from '../../services/users.service';
import { validateName, validateEmail, validateUsername, validatePassword } from '../form.validator';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  constructor(private formBuilder: FormBuilder, private usersService: UsersService,
    private router: Router, private localSS: LocalStorageService) {

    const isLoggedIn = this.localSS.getItem('user');
    if (isLoggedIn)
      this.router.navigate(['/'])
  }


  registerForm!: FormGroup
  successMessage!: string
  errorMessage!: string

  ngOnInit(): void {

    this.registerForm = this.formBuilder.group({
      fullName: ['', [Validators.required, validateName]],
      emailId: ['', [Validators.required, validateEmail]],
      username: ['', [Validators.required, validateUsername]],
      password: ['', [Validators.required, validatePassword]],
      confirmPassword: ['', [Validators.required]]
    })
  }

  get fullName() {
    return this.registerForm.controls['fullName'];
  }
  get emailId() {
    return this.registerForm.controls['emailId'];
  }
  get username() {
    return this.registerForm.controls['username'];
  }
  get password() {
    return this.registerForm.controls['password'];
  }
  get confirmPassword() {
    return this.registerForm.controls['confirmPassword'];
  }


  submit() {

    let userData: User = {
      emailId: this.registerForm.value.emailId,
      username: this.registerForm.value.username,
      password: this.registerForm.value.password,
      fullName: this.registerForm.value.fullName,
      profileImgId: 1,
    };

    this.successMessage = ''
    this.errorMessage = ''

    this.usersService.registerUser(userData)
      .subscribe(
        (res) => {
          this.successMessage = '' + res.successMessage;
          this.registerForm.reset();
          setTimeout(() => {
            this.router.navigate(['login']);
          }, 2000)
        },
        (err) => {
          this.errorMessage = err.error.errorMessage;
        }
      )
  }
}
