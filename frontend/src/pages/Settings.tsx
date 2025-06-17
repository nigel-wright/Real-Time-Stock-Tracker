
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { Switch } from "@/components/ui/switch";
import { useTheme } from "next-themes";
import { useState } from "react";
import { toast } from "sonner";
import { Trash2 } from "lucide-react";

const SettingsPage = () => {
  const { theme, setTheme } = useTheme();
  const [isSavingProfile, setIsSavingProfile] = useState(false);
  const [isUpdatingPassword, setIsUpdatingPassword] = useState(false);

  const handleProfileSave = (e: React.FormEvent) => {
    e.preventDefault();
    setIsSavingProfile(true);
    toast.promise(new Promise(resolve => setTimeout(resolve, 1500)), {
        loading: 'Saving profile...',
        success: () => {
            setIsSavingProfile(false);
            return 'Profile updated successfully!';
        },
        error: 'Failed to update profile.',
    });
  }

  const handlePasswordUpdate = (e: React.FormEvent) => {
    e.preventDefault();
    setIsUpdatingPassword(true);
    toast.promise(new Promise(resolve => setTimeout(resolve, 1500)), {
        loading: 'Updating password...',
        success: () => {
            setIsUpdatingPassword(false);
            return 'Password updated successfully!';
        },
        error: 'Failed to update password.',
    });
  }

  const savedLayouts = [
    { id: 1, name: 'My Day Trading Setup' },
    { id: 2, name: 'Swing Trading Watchlist' },
  ];

  return (
    <div className="space-y-6 max-w-2xl mx-auto">
      <div>
        <h1 className="text-2xl font-bold">My Profile</h1>
        <p className="text-muted-foreground">Manage your account and preferences.</p>
      </div>
      <Card>
        <CardHeader>
          <CardTitle>Profile</CardTitle>
          <CardDescription>Update your personal information.</CardDescription>
        </CardHeader>
        <CardContent>
          <form onSubmit={handleProfileSave} className="space-y-4">
            <div className="space-y-2">
              <Label htmlFor="name">Full Name</Label>
              <Input id="name" defaultValue="John Doe" />
            </div>
            <div className="space-y-2">
              <Label htmlFor="email">Email</Label>
              <Input id="email" type="email" defaultValue="user@example.com" />
            </div>
            <div className="space-y-2">
              <Label htmlFor="timezone">Timezone</Label>
              <Select defaultValue="est">
                <SelectTrigger>
                  <SelectValue placeholder="Select a timezone" />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="gmt">GMT</SelectItem>
                  <SelectItem value="est">EST</SelectItem>
                  <SelectItem value="pst">PST</SelectItem>
                </SelectContent>
              </Select>
            </div>
            <Button type="submit" disabled={isSavingProfile}>{isSavingProfile ? 'Saving...' : 'Save Changes'}</Button>
          </form>
        </CardContent>
      </Card>
      
      <Card>
        <CardHeader>
          <CardTitle>Password</CardTitle>
          <CardDescription>Change your password. Please enter your current password to set a new one.</CardDescription>
        </CardHeader>
        <CardContent>
            <form onSubmit={handlePasswordUpdate} className="space-y-4">
              <div className="space-y-2">
                  <Label htmlFor="current-password">Current Password</Label>
                  <Input id="current-password" type="password" />
              </div>
              <div className="space-y-2">
                  <Label htmlFor="new-password">New Password</Label>
                  <Input id="new-password" type="password" />
              </div>
              <div className="space-y-2">
                  <Label htmlFor="confirm-password">Confirm New Password</Label>
                  <Input id="confirm-password" type="password" />
              </div>
              <Button type="submit" disabled={isUpdatingPassword}>{isUpdatingPassword ? 'Updating...' : 'Update Password'}</Button>
            </form>
        </CardContent>
      </Card>

      <Card>
        <CardHeader>
          <CardTitle>Chart Layouts</CardTitle>
          <CardDescription>Manage and load your saved chart configurations.</CardDescription>
        </CardHeader>
        <CardContent>
          <div className="space-y-2">
            {savedLayouts.map(layout => (
              <div key={layout.id} className="flex items-center justify-between p-3 rounded-md border bg-background">
                <p className="text-sm font-medium">{layout.name}</p>
                <div className="flex items-center gap-2">
                  <Button variant="outline" size="sm">Load</Button>
                  <Button variant="destructive" size="icon" className="h-9 w-9">
                    <Trash2 className="h-4 w-4" />
                    <span className="sr-only">Delete layout</span>
                  </Button>
                </div>
              </div>
            ))}
          </div>
          <Button className="mt-4">Save Current Layout</Button>
        </CardContent>
      </Card>

      <Card>
        <CardHeader>
          <CardTitle>Trading Preferences</CardTitle>
          <CardDescription>Configure your default trading settings.</CardDescription>
        </CardHeader>
        <CardContent>
          <div className="space-y-2">
            <Label htmlFor="default-algo">Default Trading Algorithm</Label>
            <Select defaultValue="momentum">
              <SelectTrigger id="default-algo">
                <SelectValue placeholder="Select an algorithm" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="none">None</SelectItem>
                <SelectItem value="momentum">Momentum</SelectItem>
                <SelectItem value="mean-reversion">Mean Reversion</SelectItem>
              </SelectContent>
            </Select>
          </div>
        </CardContent>
      </Card>

      <Card>
        <CardHeader>
          <CardTitle>Notifications</CardTitle>
          <CardDescription>Manage how you receive alerts and updates.</CardDescription>
        </CardHeader>
        <CardContent className="space-y-4">
          <div className="flex items-center justify-between rounded-lg border p-4">
            <div className="space-y-0.5">
              <Label htmlFor="email-notifications" className="font-medium">Email Notifications</Label>
              <p className="text-sm text-muted-foreground">
                Receive important updates via email.
              </p>
            </div>
            <Switch id="email-notifications" defaultChecked />
          </div>
          <div className="flex items-center justify-between rounded-lg border p-4">
            <div className="space-y-0.5">
              <Label htmlFor="push-notifications" className="font-medium">Push Notifications</Label>
              <p className="text-sm text-muted-foreground">
                Get real-time alerts on your devices.
              </p>
            </div>
            <Switch id="push-notifications" disabled />
          </div>
        </CardContent>
      </Card>

      <Card>
        <CardHeader>
            <CardTitle>Theme</CardTitle>
            <CardDescription>Customize the application's appearance.</CardDescription>
        </CardHeader>
        <CardContent>
          <div className="flex items-center justify-between">
            <Label htmlFor="dark-mode" className="flex flex-col space-y-1">
              <span>Dark Mode</span>
              <span className="font-normal leading-snug text-muted-foreground">
                Toggle to switch between light and dark themes.
              </span>
            </Label>
            <Switch
              id="dark-mode"
              checked={theme === 'dark'}
              onCheckedChange={(checked) => setTheme(checked ? 'dark' : 'light')}
            />
          </div>
        </CardContent>
      </Card>
    </div>
  );
};

export default SettingsPage;
