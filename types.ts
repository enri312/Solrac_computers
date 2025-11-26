export enum DeviceType {
  NOTEBOOK = 'Notebook',
  SMARTPHONE = 'Celular',
  DESKTOP = 'PC de Mesa',
  CONSOLE = 'Consola'
}

export enum TicketStatus {
  PENDING = 'Pendiente',
  IN_PROGRESS = 'En Reparación',
  READY = 'Listo para Entregar',
  DELIVERED = 'Entregado'
}

export interface Customer {
  name: string;
  address: string;
  phone: string;
}

export interface Ticket {
  id: string;
  customer: Customer;
  deviceType: DeviceType;
  model: string;
  problemDescription: string;
  observations: string; // Additional notes
  status: TicketStatus;
  amountPaid: number;
  totalCost: number;
  dateCreated: string;
  aiDiagnosis?: string; // Stored AI suggestion
}

export type ViewState = 'LOGIN' | 'DASHBOARD' | 'NEW_TICKET' | 'LIST_TICKETS' | 'JAVA_EXPORT';

export type Language = 'es' | 'en';