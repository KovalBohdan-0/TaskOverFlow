import { Component } from '@angular/core';
import {BoardService} from "../../../service/board.service";
import {SharedService} from "../../../service/shared.service";

@Component({
  selector: 'app-sidebar-add-member',
  templateUrl: './sidebar-add-member.component.html',
  styleUrls: ['./sidebar-add-member.component.css']
})
export class SidebarAddMemberComponent {
  email;

  constructor(private boardService: BoardService, private sharedService: SharedService) {
  }

  addBoardMember() {
    this.boardService.addBoardMember(this.sharedService.boardId, this.email).subscribe({
      next: () => {}
    });
  }
}
