import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SharedService {
  boardId: number;
  sidebarOpened: boolean = false;
}
