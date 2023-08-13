import {Component} from '@angular/core';
import {BoardService} from "../../../service/board.service";
import {SharedService} from "../../../service/shared.service";

@Component({
  selector: 'app-delete-board-modal',
  templateUrl: './delete-board-modal.component.html',
  styleUrls: ['./delete-board-modal.component.css']
})
export class DeleteBoardModalComponent {
  constructor(private boardService: BoardService, private sharedService: SharedService) {
  }

  deleteBoard(): void {
    this.boardService.deleteBoard(this.sharedService.boardId).subscribe();
  }
}
