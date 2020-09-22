import { Component, OnInit } from "@angular/core";

@Component({
  selector: "app-comp-b",
  templateUrl: "./comp-b.component.html",
  styleUrls: ["./comp-b.component.scss"],
})
export class CompBComponent implements OnInit {
  constructor(private dialog: MatDialog) {}

  ngOnInit(): void {}

  open() {
    this.dialog.open(CompAComponent, {
      width: "500px",
      height: "450px",
    });
  }
}
