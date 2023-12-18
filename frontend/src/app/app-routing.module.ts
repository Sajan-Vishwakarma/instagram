import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { PasswordResetComponent } from './auth/password-reset/password-reset.component';
import { RegisterComponent } from './auth/register/register.component';
import { ExploreComponent } from './components/home/explore/explore.component';
import { FeedComponent } from './components/home/feed/feed.component';
import { HomeComponent } from './components/home/home.component';
import { CreatePostComponent } from './components/posts/create-post/create-post.component';
import { EditUserComponent } from './components/profile-page/edit-user/edit-user.component';
import { ProfileInfoComponent } from './components/profile-page/profile-info/profile-info.component';
import { ProfilePageComponent } from './components/profile-page/profile-page.component';
import { SearchComponent } from './components/search/search.component';
import { AuthGuardService } from './services/guards/auth-guard.service';

const routes: Routes = [
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  { path: 'reset-password', component: PasswordResetComponent },
  {
    path: '', component: HomeComponent, canActivate: [AuthGuardService],
    children: [
      { path: '', component: FeedComponent, canActivate: [AuthGuardService] },
      { path: 'explore', component: ExploreComponent, canActivate: [AuthGuardService] }
    ]
  },
  { path: 'search', component: SearchComponent, canActivate: [AuthGuardService] },
  { path: 'create', component: CreatePostComponent, canActivate: [AuthGuardService] },
  {
    path: 'profile', component: ProfilePageComponent, canActivate: [AuthGuardService],
    children: [
      { path: 'edit', component: EditUserComponent, canActivate: [AuthGuardService] },
      { path: ':username', component: ProfileInfoComponent, canActivate: [AuthGuardService] },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
