import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { PasswordReset } from '../../models/PasswordReset';
import { LocalStorageService } from '../../services/local-storage.service';
import { UsersService } from '../../services/users.service';
import { validatePassword } from '../form.validator';

@Component({
  selector: 'app-password-reset',
  templateUrl: './password-reset.component.html',
  styleUrl: './password-reset.component.css'
})
export class PasswordResetComponent {
  constructor(private formBuilder: FormBuilder, private usersService: UsersService,
    private router: Router, private localSS: LocalStorageService) {

    const isLoggedIn = localSS.getItem('user');
    if (isLoggedIn)
      this.router.navigate(['/'])
  }


  pwdResetForm!: FormGroup
  successMessage!: string
  errorMessage!: string

  ngOnInit(): void {

    this.pwdResetForm = this.formBuilder.group({
      emailId: ['', [Validators.required]],
      username: ['', [Validators.required]],
      password: ['', [Validators.required, validatePassword]],
      confirmPassword: ['', [Validators.required]]
    })
  }

  get emailId() {
    return this.pwdResetForm.controls['emailId'];
  }
  get username() {
    return this.pwdResetForm.controls['username'];
  }
  get password() {
    return this.pwdResetForm.controls['password'];
  }
  get confirmPassword() {
    return this.pwdResetForm.controls['confirmPassword'];
  }


  submit() {

    let pwdResetData: PasswordReset = {
      emailId: this.pwdResetForm.value.emailId,
      username: this.pwdResetForm.value.username,
      password: this.pwdResetForm.value.password
    };

    this.successMessage = ''
    this.errorMessage = ''

    this.usersService.resetPassword(pwdResetData)
      .subscribe(
        (res) => {
          this.successMessage = '' + res.successMessage;
          this.pwdResetForm.reset();
        },
        (err) => {
          this.errorMessage = err.error.errorMessage;
        }
      )

  }
}
