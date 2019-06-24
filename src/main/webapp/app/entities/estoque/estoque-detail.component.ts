import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEstoque } from 'app/shared/model/estoque.model';

@Component({
  selector: 'jhi-estoque-detail',
  templateUrl: './estoque-detail.component.html'
})
export class EstoqueDetailComponent implements OnInit {
  estoque: IEstoque;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ estoque }) => {
      this.estoque = estoque;
    });
  }

  previousState() {
    window.history.back();
  }
}
