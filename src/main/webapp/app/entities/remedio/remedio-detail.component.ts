import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRemedio } from 'app/shared/model/remedio.model';

@Component({
  selector: 'jhi-remedio-detail',
  templateUrl: './remedio-detail.component.html'
})
export class RemedioDetailComponent implements OnInit {
  remedio: IRemedio;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ remedio }) => {
      this.remedio = remedio;
    });
  }

  previousState() {
    window.history.back();
  }
}
