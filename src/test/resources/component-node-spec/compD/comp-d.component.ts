import { Component, OnInit } from "@angular/core";
import { CompBComponent } from "../compB/comp-b.component";

@Component({
  selector: "app-comp-d",
  templateUrl: "./comp-d.component.html",
  styleUrls: ["./comp-d.component.scss"],
})
export class CompDComponent implements OnInit {
  constructor(private dialog: MatDialog) {}

  ngOnInit(): void {}

  open() {
    this.dialog.open(CompBComponent, {
      width: "500px",
      height: "450px",
    });
  }
}
