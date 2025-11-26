import React, { useState } from 'react';
import { 
  LayoutDashboard, 
  PlusCircle, 
  List, 
  LogOut, 
  Wrench, 
  User, 
  Smartphone, 
  Monitor, 
  Laptop, 
  Search, 
  Sparkles,
  CheckCircle,
  Clock,
  MapPin,
  Phone,
  DollarSign,
  Globe,
  Code,
  Copy,
  Download
} from 'lucide-react';

import { DeviceType, TicketStatus, Ticket, ViewState, Language, Customer } from './types';
import { GlassCard, Input, TextArea, Button, StatusBadge } from './components/ui';
import { getRepairDiagnosis } from './services/geminiService';

// --- I18N CONFIGURATION ---
const TRANSLATIONS = {
  es: {
    loginTitle: "Ingresar al Sistema",
    user: "Usuario",
    pass: "Contraseña",
    menuMain: "Menú Principal",
    menuNew: "Nuevo Ingreso",
    menuList: "Lista de Equipos",
    menuJava: "Código Java (Exportar)",
    logout: "Salir",
    dashboardTitle: "Panel de Control",
    toFix: "Equipos para Arreglar",
    toDeliver: "Para Entregar",
    delivered: "Entregados (Historial)",
    pendingList: "Equipos en Taller (Pendientes)",
    readyList: "Listos para Entregar",
    noPending: "No hay equipos pendientes.",
    noReady: "No hay equipos para entregar.",
    registerTitle: "Registrar Equipo",
    clientData: "Datos del Cliente",
    name: "Nombre Completo",
    address: "Dirección",
    phone: "Teléfono / Celular",
    deviceData: "Datos del Equipo",
    deviceType: "Tipo de Equipo",
    model: "Modelo",
    amountPaid: "Monto Abonado ($)",
    problemDesc: "Descripción del Problema",
    consultAI: "Consultar Diagnóstico IA",
    aiRec: "Recomendación Gemini",
    observations: "Observaciones Técnicas",
    cancel: "Cancelar",
    register: "Registrar Ingreso",
    inventoryTitle: "Inventario de Equipos",
    searchPlaceholder: "Buscar cliente...",
    problem: "Problema",
    obs: "Obs",
    total: "Total",
    edit: "Editar",
    deliver: "Entregar",
    javaTitle: "Código Fuente Java (Swing/AWT)",
    javaDesc: "Copia este código en tu IDE (Antigravity, Eclipse, IntelliJ) para ejecutar la versión de escritorio.",
    copy: "Copiar Código"
  },
  en: {
    loginTitle: "System Login",
    user: "Username",
    pass: "Password",
    menuMain: "Main Menu",
    menuNew: "New Entry",
    menuList: "Device List",
    menuJava: "Java Code (Export)",
    logout: "Logout",
    dashboardTitle: "Dashboard",
    toFix: "To Repair",
    toDeliver: "To Deliver",
    delivered: "Delivered (History)",
    pendingList: "In Workshop (Pending)",
    readyList: "Ready for Delivery",
    noPending: "No pending devices.",
    noReady: "No devices ready for delivery.",
    registerTitle: "Register Device",
    clientData: "Customer Details",
    name: "Full Name",
    address: "Address",
    phone: "Phone / Mobile",
    deviceData: "Device Details",
    deviceType: "Device Type",
    model: "Model",
    amountPaid: "Amount Paid ($)",
    problemDesc: "Problem Description",
    consultAI: "Consult AI Diagnosis",
    aiRec: "Gemini Recommendation",
    observations: "Technical Observations",
    cancel: "Cancel",
    register: "Register Entry",
    inventoryTitle: "Device Inventory",
    searchPlaceholder: "Search customer...",
    problem: "Problem",
    obs: "Note",
    total: "Total",
    edit: "Edit",
    deliver: "Deliver",
    javaTitle: "Java Source Code (Swing/AWT)",
    javaDesc: "Copy this code into your IDE (Antigravity, Eclipse, IntelliJ) to run the desktop version.",
    copy: "Copy Code"
  }
};

// --- MOCK DATA ---
const INITIAL_TICKETS: Ticket[] = [
  {
    id: '1',
    customer: { name: 'Juan Perez', address: 'Av. Siempre Viva 123', phone: '0981-555-0101' },
    deviceType: DeviceType.NOTEBOOK,
    model: 'Dell XPS 15',
    problemDescription: 'La pantalla parpadea al mover la bisagra.',
    observations: 'La bisagra izquierda parece suelta.',
    status: TicketStatus.IN_PROGRESS,
    amountPaid: 50000,
    totalCost: 150000,
    dateCreated: '2023-10-25'
  },
  {
    id: '2',
    customer: { name: 'Maria Gomez', address: 'Calle Falsa 456', phone: '0971-555-0202' },
    deviceType: DeviceType.SMARTPHONE,
    model: 'iPhone 13',
    problemDescription: 'La batería se agota muy rápido.',
    observations: 'Necesita cambio de batería.',
    status: TicketStatus.READY,
    amountPaid: 0,
    totalCost: 80000,
    dateCreated: '2023-10-26'
  }
];

// --- JAVA CODE GENERATION (Templates) ---
const JAVA_TEMPLATES = {
  Main: `package com.solrac.computers;

import javax.swing.SwingUtilities;
import com.solrac.computers.view.MainFrame;

/**
 * Main Entry Point - Single Responsibility: Bootstrapping
 */
public class SolracApp {
    public static void main(String[] args) {
        // Run on Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}`,
  Model: `package com.solrac.computers.model;

import java.time.LocalDate;

public enum DeviceType {
    NOTEBOOK, SMARTPHONE, DESKTOP, CONSOLE
}

public enum TicketStatus {
    PENDING, IN_PROGRESS, READY, DELIVERED
}

/**
 * Ticket Entity - Represents the data structure
 */
public class Ticket {
    private String id;
    private String customerName;
    private String address;
    private String phone;
    private DeviceType deviceType;
    private String model;
    private String problem;
    private TicketStatus status;
    private double amountPaid;

    public Ticket(String name, String phone, DeviceType type, String model, String problem) {
        this.id = String.valueOf(System.currentTimeMillis());
        this.customerName = name;
        this.phone = phone;
        this.deviceType = type;
        this.model = model;
        this.problem = problem;
        this.status = TicketStatus.PENDING;
        this.amountPaid = 0.0;
    }

    // Getters and Setters omitted for brevity...
    public String getCustomerName() { return customerName; }
    public String getModel() { return model; }
    public TicketStatus getStatus() { return status; }
}`,
  View: `package com.solrac.computers.view;

import javax.swing.*;
import java.awt.*;

/**
 * MainFrame - Handles the main UI container (SRP: UI Layout only)
 */
public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    
    public MainFrame() {
        setTitle("Solrac_Computer's - Gestión");
        setSize(1024, 768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        setLayout(new BorderLayout());
        
        // Navigation (Sidebar)
        add(createSidebar(), BorderLayout.WEST);
        
        // Main Content Area
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(Color.decode("#0b0c15")); // Dark Theme
        
        // Add Views
        mainPanel.add(new DashboardPanel(), "DASHBOARD");
        mainPanel.add(new TicketFormPanel(), "NEW_TICKET");
        
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(Color.decode("#151725"));
        sidebar.setPreferredSize(new Dimension(200, getHeight()));
        
        JButton btnDash = new JButton("Panel de Control");
        JButton btnNew = new JButton("Nuevo Equipo");
        
        btnDash.addActionListener(e -> cardLayout.show(mainPanel, "DASHBOARD"));
        btnNew.addActionListener(e -> cardLayout.show(mainPanel, "NEW_TICKET"));
        
        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(btnDash);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(btnNew);
        
        return sidebar;
    }
}

// Placeholder for Dashboard Panel
class DashboardPanel extends JPanel {
    public DashboardPanel() {
        setOpaque(false);
        JLabel lbl = new JLabel("Bienvenido al Sistema Solrac");
        lbl.setForeground(Color.WHITE);
        add(lbl);
    }
}`,
  Controller: `package com.solrac.computers.controller;

import com.solrac.computers.model.Ticket;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TicketManager - Handles Business Logic (SRP: Logic only)
 */
public class TicketManager {
    private static TicketManager instance;
    private List<Ticket> tickets;
    
    private TicketManager() {
        tickets = new ArrayList<>();
    }
    
    public static TicketManager getInstance() {
        if (instance == null) instance = new TicketManager();
        return instance;
    }
    
    public void addTicket(Ticket t) {
        tickets.add(t);
        // Here you would save to DB or File
    }
    
    public List<Ticket> getPendingTickets() {
        return tickets.stream()
            .filter(t -> t.getStatus() == com.solrac.computers.model.TicketStatus.PENDING)
            .collect(Collectors.toList());
    }
}`
};

// --- HOOKS ---
function useTicketSystem() {
  const [tickets, setTickets] = useState<Ticket[]>(INITIAL_TICKETS);
  const [formData, setFormData] = useState<Partial<Ticket>>({
    customer: { name: '', address: '', phone: '' },
    deviceType: DeviceType.NOTEBOOK,
    status: TicketStatus.PENDING,
    amountPaid: 0,
    totalCost: 0
  });

  const createTicket = () => {
    if (!formData.customer?.name || !formData.model) return false;

    const newTicket: Ticket = {
      id: Date.now().toString(),
      customer: formData.customer as any,
      deviceType: formData.deviceType || DeviceType.NOTEBOOK,
      model: formData.model || 'Desconocido',
      problemDescription: formData.problemDescription || '',
      observations: formData.observations || '',
      status: formData.status || TicketStatus.PENDING,
      amountPaid: formData.amountPaid || 0,
      totalCost: formData.totalCost || 0,
      dateCreated: new Date().toLocaleDateString(),
      aiDiagnosis: formData.aiDiagnosis
    };

    setTickets(prev => [newTicket, ...prev]);
    resetForm();
    return true;
  };

  const resetForm = () => {
    setFormData({
      customer: { name: '', address: '', phone: '' },
      deviceType: DeviceType.NOTEBOOK,
      status: TicketStatus.PENDING,
      amountPaid: 0,
      totalCost: 0,
      aiDiagnosis: ''
    });
  };

  const updateForm = (updates: Partial<Ticket>) => {
    setFormData(prev => ({ ...prev, ...updates }));
  };

  const updateCustomer = (updates: Partial<Customer>) => {
    setFormData(prev => ({
      ...prev,
      customer: { ...prev.customer!, ...updates }
    }));
  };

  return { tickets, formData, createTicket, updateForm, updateCustomer };
}

// --- COMPONENTS ---
const TicketMiniItem = ({ ticket, colorClass, labels }: { ticket: Ticket, colorClass: string, labels: any }) => (
  <div className="flex items-center justify-between p-3 bg-white/5 rounded-lg border border-white/5 mb-2 hover:bg-white/10 transition">
      <div className="flex items-center gap-3">
          <div className={`p-2 rounded-lg ${colorClass}`}>
              {ticket.deviceType === DeviceType.NOTEBOOK ? <Laptop size={16} /> : 
               ticket.deviceType === DeviceType.SMARTPHONE ? <Smartphone size={16} /> : 
               <Monitor size={16} />}
          </div>
          <div>
              <p className="font-bold text-sm">{ticket.customer.name}</p>
              <p className="text-xs text-gray-400">{ticket.model}</p>
          </div>
      </div>
      <div className="text-right">
           <span className="text-xs font-mono block text-gray-400">{ticket.dateCreated}</span>
           <StatusBadge status={ticket.status} />
      </div>
  </div>
);

const LanguageToggle = ({ lang, setLang }: { lang: Language, setLang: (l: Language) => void }) => (
  <button 
    onClick={() => setLang(lang === 'es' ? 'en' : 'es')}
    className="flex items-center gap-2 px-3 py-1 bg-white/5 border border-white/10 rounded-full hover:bg-white/10 transition text-xs font-mono"
  >
    <Globe size={12} />
    {lang.toUpperCase()}
  </button>
);

// --- MAIN APPLICATION ---
export default function App() {
  const [view, setView] = useState<ViewState>('LOGIN');
  const [user, setUser] = useState<{ name: string } | null>(null);
  const [lang, setLang] = useState<Language>('es');
  const [isAiLoading, setIsAiLoading] = useState(false);
  
  // Java Export State
  const [activeJavaTab, setActiveJavaTab] = useState<keyof typeof JAVA_TEMPLATES>('Main');

  const { tickets, formData, createTicket, updateForm, updateCustomer } = useTicketSystem();
  const t = (key: keyof typeof TRANSLATIONS.es) => TRANSLATIONS[lang][key];

  const handleLogin = (e: React.FormEvent) => {
    e.preventDefault();
    setUser({ name: 'Técnico Admin' });
    setView('DASHBOARD');
  };

  const handleCreateTicketWrapper = () => {
    if (createTicket()) {
      setView('LIST_TICKETS');
    }
  };

  const handleAiDiagnosis = async () => {
    if (!formData.deviceType || !formData.problemDescription) return;
    setIsAiLoading(true);
    const diagnosis = await getRepairDiagnosis(formData.deviceType, formData.problemDescription);
    updateForm({ aiDiagnosis: diagnosis });
    setIsAiLoading(false);
  };

  const copyToClipboard = (text: string) => {
    navigator.clipboard.writeText(text);
    // Could add toast here
  };

  // --- VIEWS ---

  const LoginView = () => (
    <div className="min-h-screen flex items-center justify-center relative overflow-hidden">
       <div className="absolute top-1/4 left-1/4 w-64 h-64 bg-neon-blue/20 rounded-full blur-[100px] animate-pulse" />
       <div className="absolute bottom-1/4 right-1/4 w-96 h-96 bg-neon-purple/20 rounded-full blur-[120px] animate-pulse" />
       
       <div className="absolute top-6 right-6 z-20">
         <LanguageToggle lang={lang} setLang={setLang} />
       </div>

      <GlassCard className="w-full max-w-md mx-4 text-center z-10">
        <div className="mb-8">
          <div className="w-20 h-20 bg-gradient-to-tr from-neon-blue to-neon-purple rounded-2xl mx-auto flex items-center justify-center shadow-lg shadow-neon-blue/50 mb-4 animate-float">
            <Wrench className="text-white w-10 h-10" />
          </div>
          <h1 className="text-3xl font-bold bg-clip-text text-transparent bg-gradient-to-r from-white to-gray-400">
            Solrac_Computer's
          </h1>
        </div>

        <form onSubmit={handleLogin} className="space-y-6">
          <Input 
            label={t('user')} 
            placeholder="admin" 
            defaultValue="admin"
          />
          <Input 
            label={t('pass')} 
            type="password" 
            placeholder="••••••••" 
            defaultValue="password"
          />
          <Button type="submit" className="w-full">
            {t('loginTitle')}
          </Button>
        </form>
      </GlassCard>
    </div>
  );

  const DashboardView = () => {
    const toFix = tickets.filter(t => t.status === TicketStatus.PENDING || t.status === TicketStatus.IN_PROGRESS);
    const toDeliver = tickets.filter(t => t.status === TicketStatus.READY);
    const completed = tickets.filter(t => t.status === TicketStatus.DELIVERED);

    return (
      <div className="p-8 max-w-7xl mx-auto animate-fade-in">
        <div className="flex justify-between items-center mb-8">
           <h2 className="text-3xl font-bold">{t('dashboardTitle')}</h2>
           <LanguageToggle lang={lang} setLang={setLang} />
        </div>
        
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
          <GlassCard className="border-l-4 border-l-yellow-500">
            <div className="flex justify-between items-start">
              <div>
                <p className="text-gray-400 text-sm">{t('toFix')}</p>
                <h3 className="text-4xl font-bold text-white mt-2">{toFix.length}</h3>
              </div>
              <div className="p-3 bg-yellow-500/20 rounded-lg text-yellow-500">
                <Wrench size={24} />
              </div>
            </div>
          </GlassCard>

          <GlassCard className="border-l-4 border-l-neon-green" delay>
            <div className="flex justify-between items-start">
              <div>
                <p className="text-gray-400 text-sm">{t('toDeliver')}</p>
                <h3 className="text-4xl font-bold text-white mt-2">{toDeliver.length}</h3>
              </div>
              <div className="p-3 bg-neon-green/20 rounded-lg text-neon-green">
                <CheckCircle size={24} />
              </div>
            </div>
          </GlassCard>

          <GlassCard className="border-l-4 border-l-blue-500" delay>
            <div className="flex justify-between items-start">
              <div>
                <p className="text-gray-400 text-sm">{t('delivered')}</p>
                <h3 className="text-4xl font-bold text-white mt-2">{completed.length}</h3>
              </div>
              <div className="p-3 bg-blue-500/20 rounded-lg text-blue-500">
                <DollarSign size={24} />
              </div>
            </div>
          </GlassCard>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
            <div className="space-y-4">
                <h3 className="text-xl font-semibold text-yellow-400 flex items-center gap-2">
                    <Wrench size={20}/> {t('pendingList')}
                </h3>
                <GlassCard className="min-h-[200px]">
                    {toFix.length === 0 ? (
                        <p className="text-gray-500 text-center py-4">{t('noPending')}</p>
                    ) : (
                        toFix.map(tk => <TicketMiniItem key={tk.id} ticket={tk} colorClass="bg-yellow-500/20 text-yellow-500" labels={t} />)
                    )}
                </GlassCard>
            </div>

            <div className="space-y-4">
                <h3 className="text-xl font-semibold text-neon-green flex items-center gap-2">
                    <CheckCircle size={20}/> {t('readyList')}
                </h3>
                <GlassCard className="min-h-[200px]">
                     {toDeliver.length === 0 ? (
                        <p className="text-gray-500 text-center py-4">{t('noReady')}</p>
                    ) : (
                        toDeliver.map(tk => <TicketMiniItem key={tk.id} ticket={tk} colorClass="bg-neon-green/20 text-neon-green" labels={t} />)
                    )}
                </GlassCard>
            </div>
        </div>
      </div>
    );
  };

  const NewTicketView = () => (
    <div className="p-8 max-w-4xl mx-auto animate-fade-in">
      <div className="flex justify-between items-center mb-8">
        <h2 className="text-3xl font-bold">{t('registerTitle')}</h2>
        <LanguageToggle lang={lang} setLang={setLang} />
      </div>

      <GlassCard className="p-8">
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div className="space-y-4">
            <h3 className="text-neon-blue font-semibold flex items-center gap-2 mb-4">
              <User size={18} /> {t('clientData')}
            </h3>
            <Input 
              label={t('name')} 
              value={formData.customer?.name} 
              onChange={e => updateCustomer({ name: e.target.value })}
            />
            <Input 
              label={t('address')} 
              value={formData.customer?.address} 
              onChange={e => updateCustomer({ address: e.target.value })}
            />
            <Input 
              label={t('phone')} 
              value={formData.customer?.phone} 
              onChange={e => updateCustomer({ phone: e.target.value })}
            />
          </div>

          <div className="space-y-4">
            <h3 className="text-neon-blue font-semibold flex items-center gap-2 mb-4">
              <Monitor size={18} /> {t('deviceData')}
            </h3>
            <div className="mb-4">
              <label className="block text-gray-400 text-sm mb-1">{t('deviceType')}</label>
              <select 
                className="w-full bg-space-800/50 border border-white/10 rounded-lg px-4 py-3 text-white focus:outline-none focus:border-neon-blue"
                value={formData.deviceType}
                onChange={e => updateForm({ deviceType: e.target.value as DeviceType })}
              >
                {Object.values(DeviceType).map(type => (
                  <option key={type} value={type}>{type}</option>
                ))}
              </select>
            </div>
            <Input 
              label={t('model')} 
              value={formData.model || ''} 
              onChange={e => updateForm({ model: e.target.value })}
            />
            <Input 
              label={t('amountPaid')} 
              type="number"
              value={formData.amountPaid || ''} 
              onChange={e => updateForm({ amountPaid: parseFloat(e.target.value) })}
            />
          </div>
        </div>

        <div className="mt-8 space-y-4">
           <h3 className="text-neon-blue font-semibold flex items-center gap-2 mb-4">
              <Wrench size={18} /> {t('problemDesc')}
            </h3>
          
          <TextArea 
            label={t('problemDesc')} 
            rows={3}
            value={formData.problemDescription || ''}
            onChange={e => updateForm({ problemDescription: e.target.value })}
          />
          
          <div className="flex justify-end mb-4">
            <Button 
              type="button" 
              variant="secondary" 
              onClick={handleAiDiagnosis}
              isLoading={isAiLoading}
              className="text-sm py-2"
            >
              <Sparkles size={16} /> {t('consultAI')}
            </Button>
          </div>

          {formData.aiDiagnosis && (
            <div className="bg-neon-blue/10 border border-neon-blue/20 rounded-lg p-4 mb-4 text-sm text-gray-300">
              <h4 className="font-bold text-neon-blue mb-2 flex items-center gap-2">
                <Sparkles size={14} /> {t('aiRec')}:
              </h4>
              <div className="whitespace-pre-line">{formData.aiDiagnosis}</div>
            </div>
          )}

          <TextArea 
            label={t('observations')} 
            rows={3}
            value={formData.observations || ''}
            onChange={e => updateForm({ observations: e.target.value })}
          />
        </div>

        <div className="mt-8 pt-8 border-t border-white/10 flex justify-end gap-4">
          <Button variant="ghost" onClick={() => setView('DASHBOARD')}>{t('cancel')}</Button>
          <Button onClick={handleCreateTicketWrapper}>{t('register')}</Button>
        </div>
      </GlassCard>
    </div>
  );

  const TicketsListView = () => (
    <div className="p-8 max-w-7xl mx-auto animate-fade-in">
      <div className="flex justify-between items-center mb-8">
        <h2 className="text-3xl font-bold">{t('inventoryTitle')}</h2>
        <div className="flex gap-2 items-center">
            <LanguageToggle lang={lang} setLang={setLang} />
            <div className="relative">
                <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-500" size={18} />
                <input 
                    type="text" 
                    placeholder={t('searchPlaceholder')} 
                    className="pl-10 bg-white/5 border border-white/10 rounded-lg px-4 py-2 text-white focus:outline-none focus:border-neon-blue"
                />
            </div>
        </div>
      </div>

      <div className="grid gap-4">
        {tickets.map((ticket) => (
          <GlassCard key={ticket.id} className="flex flex-col lg:flex-row gap-6 hover:bg-white/10 transition-colors">
            <div className={`absolute left-0 top-0 bottom-0 w-1 rounded-l-2xl ${
                ticket.status === TicketStatus.READY ? 'bg-neon-green' :
                ticket.status === TicketStatus.IN_PROGRESS ? 'bg-blue-500' :
                'bg-yellow-500'
            }`} />

            <div className="flex-1 space-y-2">
                <div className="flex items-center gap-3">
                    <span className="text-2xl font-bold">{ticket.customer.name}</span>
                    <StatusBadge status={ticket.status} />
                </div>
                <div className="flex flex-wrap gap-4 text-sm text-gray-400">
                    <span className="flex items-center gap-1"><MapPin size={14}/> {ticket.customer.address}</span>
                    <span className="flex items-center gap-1"><Phone size={14}/> {ticket.customer.phone}</span>
                    <span className="flex items-center gap-1"><Clock size={14}/> {ticket.dateCreated}</span>
                </div>
                <div className="mt-4 p-3 bg-black/20 rounded-lg border border-white/5">
                    <p className="text-sm text-gray-300"><span className="text-neon-blue font-semibold">{t('problem')}:</span> {ticket.problemDescription}</p>
                    {ticket.observations && (
                         <p className="text-sm text-gray-400 mt-1"><span className="text-gray-500 font-semibold">{t('obs')}:</span> {ticket.observations}</p>
                    )}
                </div>
            </div>

            <div className="flex flex-col gap-3 min-w-[200px] border-l border-white/5 pl-0 lg:pl-6">
                <div className="flex items-center gap-2 text-gray-300">
                    {ticket.deviceType === DeviceType.NOTEBOOK ? <Laptop size={18} /> : 
                     ticket.deviceType === DeviceType.SMARTPHONE ? <Smartphone size={18} /> : 
                     <Monitor size={18} />}
                    <span className="font-medium">{ticket.model}</span>
                </div>
                
                <div className="mt-auto">
                    <div className="flex justify-between text-sm mb-1">
                        <span className="text-gray-500">{t('amountPaid')}:</span>
                        <span className="text-green-400 font-mono">${ticket.amountPaid}</span>
                    </div>
                     <div className="flex justify-between text-sm">
                        <span className="text-gray-500">{t('total')}:</span>
                        <span className="text-white font-mono">${ticket.totalCost}</span>
                    </div>
                </div>

                <div className="flex gap-2 mt-2">
                    {ticket.status !== TicketStatus.DELIVERED && (
                        <Button variant="secondary" className="w-full text-xs py-2">{t('edit')}</Button>
                    )}
                     {ticket.status === TicketStatus.READY && (
                        <Button className="w-full text-xs py-2 !bg-neon-green/20 !text-neon-green hover:!bg-neon-green/30 border-neon-green/50">{t('deliver')}</Button>
                    )}
                </div>
            </div>
          </GlassCard>
        ))}
      </div>
    </div>
  );

  const JavaExportView = () => (
    <div className="p-8 max-w-6xl mx-auto animate-fade-in">
       <div className="flex justify-between items-center mb-8">
        <div>
          <h2 className="text-3xl font-bold flex items-center gap-3">
             <Code className="text-neon-purple" /> 
             {t('javaTitle')}
          </h2>
          <p className="text-gray-400 mt-2">{t('javaDesc')}</p>
        </div>
        <LanguageToggle lang={lang} setLang={setLang} />
      </div>

      <GlassCard className="min-h-[600px] flex flex-col">
        {/* Tabs */}
        <div className="flex gap-2 border-b border-white/10 pb-4 mb-4">
           {Object.keys(JAVA_TEMPLATES).map((key) => (
             <button
               key={key}
               onClick={() => setActiveJavaTab(key as keyof typeof JAVA_TEMPLATES)}
               className={`px-4 py-2 rounded-lg font-mono text-sm transition-all ${
                 activeJavaTab === key 
                   ? 'bg-neon-purple/20 text-neon-purple border border-neon-purple/40' 
                   : 'text-gray-400 hover:text-white hover:bg-white/5'
               }`}
             >
               {key}.java
             </button>
           ))}
        </div>

        {/* Code Area */}
        <div className="relative flex-1 bg-[#1e1e1e] rounded-xl p-4 overflow-auto border border-white/10 font-mono text-sm shadow-inner">
           <button 
             onClick={() => copyToClipboard(JAVA_TEMPLATES[activeJavaTab])}
             className="absolute top-4 right-4 p-2 bg-white/10 hover:bg-white/20 rounded-lg text-white transition-colors"
             title={t('copy')}
           >
             <Copy size={16} />
           </button>
           
           <pre className="text-blue-300">
             <code>
               {JAVA_TEMPLATES[activeJavaTab]}
             </code>
           </pre>
        </div>

        <div className="mt-6 flex justify-end">
            <Button className="flex items-center gap-2">
                <Download size={18} /> Download .zip (Simulated)
            </Button>
        </div>
      </GlassCard>
    </div>
  );

  const Sidebar = () => (
    <div className="fixed left-0 top-0 h-full w-64 bg-space-800/80 backdrop-blur-md border-r border-white/5 p-6 z-20 hidden md:flex flex-col">
      <div className="flex items-center gap-3 mb-10">
        <div className="w-10 h-10 bg-gradient-to-tr from-neon-blue to-neon-purple rounded-lg flex items-center justify-center shadow-lg shadow-neon-blue/20">
          <Wrench className="text-white w-5 h-5" />
        </div>
        <span className="font-bold text-xl tracking-wide">Solrac's</span>
      </div>

      <nav className="flex-1 space-y-2">
        <NavButton active={view === 'DASHBOARD'} onClick={() => setView('DASHBOARD')} icon={<LayoutDashboard size={20} />}>
          {t('menuMain')}
        </NavButton>
        <NavButton active={view === 'NEW_TICKET'} onClick={() => setView('NEW_TICKET')} icon={<PlusCircle size={20} />}>
          {t('menuNew')}
        </NavButton>
        <NavButton active={view === 'LIST_TICKETS'} onClick={() => setView('LIST_TICKETS')} icon={<List size={20} />}>
          {t('menuList')}
        </NavButton>
        
        <div className="pt-4 mt-4 border-t border-white/10">
           <NavButton active={view === 'JAVA_EXPORT'} onClick={() => setView('JAVA_EXPORT')} icon={<Code size={20} />}>
             {t('menuJava')}
           </NavButton>
        </div>
      </nav>

      <div className="pt-6 border-t border-white/10">
        <div className="flex items-center gap-3 mb-4 text-gray-400 px-4">
          <User size={16} />
          <span className="text-sm">{user?.name}</span>
        </div>
        <Button variant="ghost" className="w-full justify-start text-red-400 hover:text-red-300 hover:bg-red-500/10" onClick={() => setView('LOGIN')}>
          <LogOut size={18} className="mr-2" /> {t('logout')}
        </Button>
      </div>
    </div>
  );

  const NavButton = ({ active, onClick, icon, children }: any) => (
    <button
      onClick={onClick}
      className={`w-full flex items-center gap-3 px-4 py-3 rounded-xl transition-all duration-300 ${
        active 
        ? 'bg-neon-blue/10 text-neon-blue border border-neon-blue/20 shadow-lg shadow-neon-blue/10' 
        : 'text-gray-400 hover:bg-white/5 hover:text-white'
      }`}
    >
      {icon}
      <span className="font-medium">{children}</span>
    </button>
  );

  if (view === 'LOGIN') {
    return <LoginView />;
  }

  return (
    <div className="min-h-screen bg-space-900 text-white font-sans selection:bg-neon-blue/30">
      <div className="fixed top-0 left-0 w-full h-full overflow-hidden -z-10 pointer-events-none">
         <div className="absolute top-0 right-0 w-[500px] h-[500px] bg-blue-900/20 rounded-full blur-[128px]" />
         <div className="absolute bottom-0 left-0 w-[500px] h-[500px] bg-purple-900/20 rounded-full blur-[128px]" />
      </div>

      <Sidebar />
      
      <main className="md:ml-64 min-h-screen pb-12">
        <header className="md:hidden p-4 border-b border-white/10 flex justify-between items-center bg-space-900/80 backdrop-blur-md sticky top-0 z-30">
             <div className="flex items-center gap-2">
                <Wrench className="text-neon-blue" />
                <span className="font-bold">Solrac's</span>
             </div>
             <Button variant="ghost" size="sm" onClick={() => setView('LOGIN')}><LogOut size={20}/></Button>
        </header>
        
        {view === 'DASHBOARD' && <DashboardView />}
        {view === 'NEW_TICKET' && <NewTicketView />}
        {view === 'LIST_TICKETS' && <TicketsListView />}
        {view === 'JAVA_EXPORT' && <JavaExportView />}
      </main>
    </div>
  );
}