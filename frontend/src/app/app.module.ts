import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms'
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { LoginComponent } from './auth/login/login.component';
import { PasswordResetComponent } from './auth/password-reset/password-reset.component';
import { RegisterComponent } from './auth/register/register.component';
import { HomeComponent } from './components/home/home.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { ExploreComponent } from './components/home/explore/explore.component';
import { FeedComponent } from './components/home/feed/feed.component';
import { CreatePostComponent } from './components/posts/create-post/create-post.component';
import { PostComponent } from './components/posts/post/post.component';
import { ProfilePageComponent } from './components/profile-page/profile-page.component';
import { EditUserComponent } from './components/profile-page/edit-user/edit-user.component';
import { ProfileInfoComponent } from './components/profile-page/profile-info/profile-info.component';
import { ProfilePostComponent } from './components/profile-page/profile-post/profile-post.component';
import { UserListComponent } from './components/profile-page/user-list/user-list.component';
import { SearchComponent } from './components/search/search.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    PasswordResetComponent,
    RegisterComponent,
    HomeComponent,
    NavbarComponent,
    ExploreComponent,
    FeedComponent,
    CreatePostComponent,
    PostComponent,
    ProfilePageComponent,
    EditUserComponent,
    ProfileInfoComponent,
    ProfilePostComponent,
    UserListComponent,
    SearchComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
