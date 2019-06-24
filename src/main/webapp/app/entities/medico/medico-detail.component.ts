import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMedico } from 'app/shared/model/medico.model';

@Component({
  selector: 'jhi-medico-detail',
  templateUrl: './medico-detail.component.html'
})
export class MedicoDetailComponent implements OnInit {
  medico: IMedico;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ medico }) => {
      this.medico = medico;
    });
  }

  previousState() {
    window.history.back();
  }
}
