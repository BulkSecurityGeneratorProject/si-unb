import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IMedico } from 'app/shared/model/medico.model';
import { AccountService } from 'app/core';
import { MedicoService } from './medico.service';

@Component({
  selector: 'jhi-medico',
  templateUrl: './medico.component.html'
})
export class MedicoComponent implements OnInit, OnDestroy {
  medicos: IMedico[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected medicoService: MedicoService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.medicoService
      .query()
      .pipe(
        filter((res: HttpResponse<IMedico[]>) => res.ok),
        map((res: HttpResponse<IMedico[]>) => res.body)
      )
      .subscribe(
        (res: IMedico[]) => {
          this.medicos = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInMedicos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IMedico) {
    return item.id;
  }

  registerChangeInMedicos() {
    this.eventSubscriber = this.eventManager.subscribe('medicoListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
