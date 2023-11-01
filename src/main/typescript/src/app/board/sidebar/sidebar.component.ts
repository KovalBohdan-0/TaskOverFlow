import {Component, OnInit} from '@angular/core';
import {BoardService} from "../../service/board.service";
import {SidebarAddMemberComponent} from "./sidebar-add-member/sidebar-add-member.component";
import {MatDialog} from "@angular/material/dialog";
import {SharedService} from "../../service/shared.service";
import {Router} from "@angular/router";
import {DeleteBoardModalComponent} from "./delete-board-modal/delete-board-modal.component";

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {
  boards = [];

  constructor(private boardService: BoardService, public dialog: MatDialog, public sharedService: SharedService,
              private router: Router) { }

  ngOnInit() {
    this.getBoards();
  }

  getBoards() {
    this.boardService.getAllBoards().subscribe({
      next: (response: any) => {
        this.boards = response.body;
      }
    });
  }

  openSettings() {
    this.router.navigate(['/settings']);
  }

  closeSidebar() {
    this.sharedService.sidebarOpened = false;
  }

  openAddBoardModal() {
    this.dialog.open(SidebarAddMemberComponent);
  }

  openDeleteBoardModal() {
    this.dialog.open(DeleteBoardModalComponent);
  }

  concentrateOnCreateBoard() {
    document.getElementById("add-board").focus();
  }
}
