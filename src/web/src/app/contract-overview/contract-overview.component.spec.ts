import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContractOverviewComponent } from './contract-overview.component';

describe('ContractOverviewComponent', () => {
  let component: ContractOverviewComponent;
  let fixture: ComponentFixture<ContractOverviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ContractOverviewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ContractOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
