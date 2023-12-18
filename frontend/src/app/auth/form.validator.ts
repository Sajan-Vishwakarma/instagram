import { AbstractControl, ValidationErrors, ValidatorFn } from "@angular/forms";

export const validateName: ValidatorFn = (control: AbstractControl): ValidationErrors | null =>{
    let fullName = control.value;
    const regex = /([A-Z][a-z]*)( [A-Z][a-z]*)*/
    if( !regex.test(fullName)){
        return { fullNameInvalid: true }
    }
    return null;
}

export const validateEmail: ValidatorFn = (control: AbstractControl): ValidationErrors | null =>{
    let email = control.value;
    const regex = /([a-z0-9]+)[@]([a-z0-9]+)[.](com|in|org)/
    if( !regex.test(email)){
        return { emailInvalid: true }
    }
    return null;
}

export const validateUsername: ValidatorFn = (control: AbstractControl): ValidationErrors | null =>{
    let username = control.value;
    const regex = /^[a-z0-9@]+$/
    if( !regex.test(username)){
        return { usernameInvalid: true }
    }
    return null;
}

export const validatePassword: ValidatorFn = (control: AbstractControl): ValidationErrors | null =>{
    let password = control.value;
    const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/
    if( !regex.test(password)){
        return { passwordInvalid: true }
    }
    return null;
}